# Tutorial: MS-Biblioteca — Do zero à reserva e devolução de livro

Este tutorial mostra o fluxo completo da aplicação usando os microserviços, passando pelo API Gateway. Todos os comandos usam `curl`; você também pode usar o Postman/Insomnia.

---

## Arquitetura dos Serviços

```
Cliente (você)
      ↓  porta 8080
  [ Gateway ]
      ↓  roteamento via Eureka
  ┌─────────────────────────────────────┐
  │  AuthTec   :9004  /auth/**          │
  │  UserTec   :9001  /api/user/**      │
  │  BookTec 1 :9002  /api/book/**      │ ← Eureka: BOOKTEC (load balancer)
  │  BookTec 2 :9005  /api/book/**      │ ←
  │  BorrowTec :9003  /api/emprestimo/**│
  └─────────────────────────────────────┘
      ↑  todos se registram em
  [ Eureka Discovery ] :8761
```

---

## Parte 1 — Subindo o Ambiente com Docker

### Pré-requisitos
- Docker 20+ e Docker Compose v2+
- ~4 GB de RAM disponíveis

### Subir todos os serviços

```bash
# No diretório raiz do projeto (onde está o docker-compose.yml)
docker compose up --build -d
```

A ordem de inicialização é gerenciada pelo `depends_on`. Aguarde ~90 segundos para todos os serviços subirem e se registrarem no Eureka.

### Verificar o Eureka (Discovery)

Abra no navegador: **http://localhost:8761**

Você deve ver todos os serviços registrados:
- `AUTHTEC`
- `USERTEC`
- `BOOKTEC` — **2 instâncias** (booktec1 e booktec2)
- `BORROWTEC`
- `GATAWAYTEC`

### Verificar logs de um serviço

```bash
docker compose logs -f booktec1
docker compose logs -f booktec2
```

### Parar o ambiente

```bash
docker compose down
# Para remover os volumes (apaga os dados H2):
docker compose down -v
```

---

## Parte 2 — Fluxo Completo via Gateway (porta 8080)

> Todos os requests abaixo passam pelo **Gateway na porta 8080**.
> O token JWT é exigido em todas as rotas exceto login e cadastro de usuário.

---

### Passo 1 — Cadastrar um Usuário

```bash
curl -s -X POST http://localhost:8080/api/user \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "login": "joaosilva",
    "senha": "Senha123",
    "ehAdministrador": true
  }' | jq
```

**Resposta esperada (201):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "tipoUsuario": "Administrador"
}
```

> **Regras de senha:** mínimo 8 caracteres, com maiúsculas, minúsculas e números.

---

### Passo 2 — Fazer Login e Obter o Token JWT

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "joaosilva",
    "senha": "Senha123"
  }')

echo "Token: $TOKEN"
```

O token retornado é uma string JWT. Guarde-o para os próximos passos.

> **Internamente:** O Gateway roteia para `AuthTec`, que chama `UserTec` via Feign para validar as credenciais, gera o JWT com claims `id`, `nome` e `papel`, e retorna a string do token.

---

### Passo 3 — Cadastrar um Gênero Literário

```bash
curl -s -X POST http://localhost:8080/api/genero \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"nome": "Romance"}' | jq
```

**Resposta esperada (201):**
```json
{ "id": 1, "nome": "Romance" }
```

---

### Passo 4 — Cadastrar um Livro

```bash
curl -s -X POST http://localhost:8080/api/book \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "isbn": "978-8535902778",
    "titulo": "Dom Casmurro",
    "autor": "Machado de Assis",
    "editora": "Companhia das Letras",
    "colecao": "Clássicos Brasileiros",
    "anoLancamento": 1899,
    "numeroExemplares": 5,
    "generoId": 1
  }' | jq
```

**Resposta esperada (201):**
```json
{
  "isbn": "978-8535902778",
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "editora": "Companhia das Letras",
  "colecao": "Clássicos Brasileiros",
  "anoLancamento": 1899,
  "numeroExemplares": 5,
  "numeroEmprestado": 0,
  "genero": "Romance"
}
```

> **Observação load balancing:** O Gateway distribui a requisição entre `booktec1` e `booktec2`. Como ambas as instâncias compartilham o mesmo arquivo H2 (via `AUTO_SERVER=TRUE` e volume Docker), o livro estará disponível em qualquer instância.

---

### Passo 5 — Confirmar o Livro nas Duas Instâncias

Faça o request de consulta algumas vezes. O Gateway vai alternando entre `booktec1` e `booktec2`:

```bash
# Execute 4 vezes para garantir que vai em ambas instâncias
for i in 1 2 3 4; do
  echo "Request $i:"
  curl -s http://localhost:8080/api/book/978-8535902778 \
    -H "Authorization: Bearer $TOKEN" | jq '.titulo, .numeroExemplares'
done
```

---

### Passo 6 — Reservar (Emprestar) o Livro

```bash
curl -s -X POST http://localhost:8080/api/emprestimo \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "idUsuario": 1,
    "isbnLivro": "978-8535902778",
    "dataDevolucao": "2026-07-01T23:59:59"
  }' | jq
```

**Resposta esperada (201):**
```json
{
  "id": 1,
  "idUsuario": 1,
  "isbnLivro": "978-8535902778",
  "dataEmprestimo": "2026-06-09T10:30:00",
  "dataDevolucao": "2026-07-01T23:59:59"
}
```

> **Internamente:**
> 1. `BorrowTec` chama `BookTec` via Feign para verificar se há exemplares disponíveis
> 2. `BorrowTec` chama `BookTec` via RestTemplate PATCH para decrementar `numeroExemplares`
> 3. Ambas as chamadas propagam o token JWT para o `BookTec`
> 4. O registro de empréstimo é salvo no banco do `BorrowTec`

---

### Passo 7 — Confirmar Atualização do Estoque

```bash
curl -s http://localhost:8080/api/book/978-8535902778 \
  -H "Authorization: Bearer $TOKEN" | jq '{titulo, numeroExemplares, numeroEmprestado}'
```

**Resposta esperada:**
```json
{
  "titulo": "Dom Casmurro",
  "numeroExemplares": 4,
  "numeroEmprestado": 1
}
```

---

### Passo 8 — Devolver o Livro

```bash
curl -s -X PATCH http://localhost:8080/api/emprestimo/1/devolver \
  -H "Authorization: Bearer $TOKEN" | jq
```

**Resposta esperada (200):**
```json
{
  "id": 1,
  "idUsuario": 1,
  "isbnLivro": "978-8535902778",
  "dataEmprestimo": "2026-06-09T10:30:00",
  "dataDevolucao": "2026-06-09T10:45:00"
}
```

A `dataDevolucao` é atualizada para o momento da devolução real.

---

### Passo 9 — Confirmar Estoque Restaurado

```bash
curl -s http://localhost:8080/api/book/978-8535902778 \
  -H "Authorization: Bearer $TOKEN" | jq '{titulo, numeroExemplares, numeroEmprestado}'
```

**Resposta esperada:**
```json
{
  "titulo": "Dom Casmurro",
  "numeroExemplares": 5,
  "numeroEmprestado": 0
}
```

---

## Parte 3 — Verificando o Load Balancing do BookTec

Para visualizar o balanceamento de carga entre as duas instâncias:

```bash
# Ver os logs em tempo real das duas instâncias ao mesmo tempo
docker compose logs -f booktec1 booktec2
```

Enquanto os logs estão abertos, faça várias requisições ao `/api/book`:

```bash
for i in $(seq 1 10); do
  curl -s http://localhost:8080/api/book \
    -H "Authorization: Bearer $TOKEN" > /dev/null
  echo "Request $i enviado"
done
```

Você verá os logs alternando entre `booktec1` e `booktec2`, demonstrando o round-robin do Eureka.

---

## Parte 4 — Swagger UI (Execução Local sem Docker)

Com os serviços rodando localmente, acesse a documentação interativa:

| Serviço | URL |
|---------|-----|
| AuthTec | http://localhost:9004/swagger-ui/index.html |
| UserTec | http://localhost:9001/swagger-ui/index.html |
| BookTec | http://localhost:9002/swagger-ui/index.html |
| BorrowTec | http://localhost:9003/swagger-ui/index.html |


