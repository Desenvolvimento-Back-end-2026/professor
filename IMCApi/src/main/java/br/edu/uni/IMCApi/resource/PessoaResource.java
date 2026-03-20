package br.edu.uni.IMCApi.resource;

import br.edu.uni.IMCApi.model.Pessoa;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    static ArrayList<Pessoa> pessoas = new ArrayList<>();

    static{
        pessoas.add(new Pessoa("Zezin da Silva ",
                180, 1.40 )) ;
        pessoas.add(new Pessoa("Pedrin",
                100, 1.80 )) ;
        pessoas.add(new Pessoa("Jefin",
                140, 2.00 )) ;
    }

    @GetMapping("")
    public String home(
            @RequestParam(required = false,
            defaultValue = "")
            String version){
        if (version.equals("01")){
            return "Nova Versão do serviço";
        }else {
            return "servidor está on";
        }
    }


    @GetMapping("/count")
    public String getPEssoasCount(@RequestHeader String token){
        if (token.contains("GYK") &&
        token.charAt(5)== 'K' && token.length() == 10){
            return pessoas.size()+"";
        }
        return "Acesso não autorizado";
    }


    @GetMapping("/v1/count")
    public String getPEssoasCountv1(
            @RequestHeader String token){
        if (token.contains("HHH") &&
                token.charAt(5)== 'K' && token.length() == 10){
            return pessoas.size()+"";
        }
        return "Acesso não autorizado";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String Salvar(
            @RequestBody @Valid Pessoa p){
        pessoas.add(p);
        return "Pessoa Salvo com sucesso";
    }
    @GetMapping("/all")
    public List<Pessoa> getPEssoas(){
        return pessoas;
    }

    @PutMapping("/{id}/mudasexo")
    public Pessoa changeSexo(@PathVariable int id){
        pessoas.get(id).setSexo( !pessoas.get(id).isSexo() );
        return pessoas.get(id);
    }

    @PatchMapping("/{id}")
    public String Editar(@PathVariable int id, @RequestBody Pessoa p){
        pessoas.set(id, p);
        return "Pessoa alterada com sucesso";
    }

    @DeleteMapping("/{id}")
    public String apagar(@PathVariable int id){
        pessoas.remove(id);
        return "Pessoa apagada com sucesso";
    }

    @GetMapping("/dados")
    public Pessoa getPEssoa(){
        Pessoa p1 = new Pessoa("Zezin da Silva ",
                180, 1.40 );

        return p1;
    }

    @GetMapping("/{id}")
    public Pessoa retornaPessoa(@PathVariable("id") int id){
        return pessoas.get(id);
    }

    @GetMapping("/search")
    public ArrayList<Pessoa> buscaPessoas(@RequestParam("nome")String nome){
        ArrayList<Pessoa> aux = new ArrayList<>();
        for(Pessoa p : pessoas){
            if (p.getNome().toUpperCase().contains(nome.toUpperCase())){
                aux.add(p);
            }
        }
        return aux;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String trataExcecao(Exception e) {
        System.out.println(e.getMessage());
        return  e.getMessage();
    }

}
