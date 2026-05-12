package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.dto.EmprestimoCreateDto;
import br.uni.ms.libtec.borrowTec.dto.EmprestimoListDto;
import br.uni.ms.libtec.borrowTec.model.Emprestimo;
import br.uni.ms.libtec.borrowTec.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository eRepo;

    private final String BOOK_API_URL = "http://localhost:9002/api/book";

    public List<EmprestimoListDto> getAll() throws Exception {
        List<Emprestimo> emprestimos = eRepo.findAll();
        if (emprestimos.isEmpty()) {
            throw new Exception("Não há empréstimos cadastrados");
        }
        return emprestimos.stream()
                .map(this::mapToListDto)
                .collect(Collectors.toList());
    }

    public EmprestimoListDto getOne(int id) {
        Emprestimo emprestimo = eRepo.findById(id).orElseThrow();
        return mapToListDto(emprestimo);
    }

    public EmprestimoListDto save(EmprestimoCreateDto dto) throws Exception {
        // Regra de negócio: Notificar o microsserviço de livros para incrementar a quantidade
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(BOOK_API_URL + "/" + dto.getIsbnLivro() + "/emprestar", null, String.class);
        } catch (Exception ex) {
            throw new Exception("Erro ao registrar empréstimo no inventário de livros: " + ex.getMessage());
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdUsuario(dto.getIdUsuario());
        emprestimo.setIsbnLivro(dto.getIsbnLivro());
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setDataDevolucao(dto.getDataDevolucao());

        emprestimo = eRepo.save(emprestimo);

        return mapToListDto(emprestimo);
    }

    public EmprestimoListDto edit(int id, EmprestimoCreateDto dto) {
        Emprestimo emprestimo = eRepo.findById(id).orElseThrow();
        
        // Se houver alteração de livro, seria necessário tratar a devolução do antigo e empréstimo do novo.
        // Por simplicidade neste CRUD básico, atualizaremos apenas os dados locais
        emprestimo.setIdUsuario(dto.getIdUsuario());
        emprestimo.setIsbnLivro(dto.getIsbnLivro());
        emprestimo.setDataDevolucao(dto.getDataDevolucao());

        emprestimo = eRepo.save(emprestimo);

        return mapToListDto(emprestimo);
    }

    public EmprestimoListDto delete(int id) throws Exception {
        Emprestimo emprestimo = eRepo.findById(id).orElseThrow();
        
        // Regra de negócio: Notificar o microsserviço de livros para decrementar a quantidade (devolução)
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(BOOK_API_URL + "/" + emprestimo.getIsbnLivro() + "/devolver", null, String.class);
        } catch (Exception ex) {
            throw new Exception("Erro ao registrar devolução no inventário de livros: " + ex.getMessage());
        }

        EmprestimoListDto dto = mapToListDto(emprestimo);
        eRepo.delete(emprestimo);
        return dto;
    }

    private EmprestimoListDto mapToListDto(Emprestimo e) {
        return new EmprestimoListDto(e.getId(), e.getIdUsuario(), e.getIsbnLivro(), e.getDataEmprestimo(), e.getDataDevolucao());
    }
}
