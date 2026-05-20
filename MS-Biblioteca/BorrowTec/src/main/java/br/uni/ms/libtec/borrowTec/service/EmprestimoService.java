package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.client.BookClient;
import br.uni.ms.libtec.borrowTec.dto.BookSimpleListDto;
import br.uni.ms.libtec.borrowTec.dto.EmprestimoCreateDto;
import br.uni.ms.libtec.borrowTec.dto.EmprestimoListDto;
import br.uni.ms.libtec.borrowTec.model.Emprestimo;
import br.uni.ms.libtec.borrowTec.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository eRepo;

    @Autowired
    RestTemplate rt;

    @Autowired
    BookClient bookclient;

    final String URL_BOOK = "http://BOOKTEC/api/book";

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


        BookSimpleListDto bookCheck = null;
        try {
            //bookCheck = rt.getForObject(URL_BOOK + "/" + dto.getIsbnLivro(), BookSimpleListDto.class);
              bookCheck = bookclient.getOneBook(dto.getIsbnLivro()).getBody();
        } catch (Exception e) {
            throw new Exception("Livro não encontrado no microsserviço.");
        }

        if (bookCheck == null || bookCheck.getNumeroExemplares() <= 0) {
            throw new Exception("Livro não disponível para empréstimo. Sem exemplares.");
        }

        BookSimpleListDto book = rt.patchForObject(
                URL_BOOK+"/"+dto.getIsbnLivro()+"/emprestar",
                null,
                BookSimpleListDto.class
                );

//        BookSimpleListDto book = bookclient.emprestaBook(dto.getIsbnLivro()).getBody();

        if (book == null){
            throw new Exception("Falha na comunicação com microsserviço de livros");
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
        
        emprestimo.setIdUsuario(dto.getIdUsuario());
        emprestimo.setIsbnLivro(dto.getIsbnLivro());
        emprestimo.setDataDevolucao(dto.getDataDevolucao());

        emprestimo = eRepo.save(emprestimo);

        return mapToListDto(emprestimo);
    }

    public EmprestimoListDto delete(int id) throws Exception {
        Emprestimo emprestimo = eRepo.findById(id).orElseThrow();

        EmprestimoListDto dto = mapToListDto(emprestimo);
        eRepo.delete(emprestimo);
        return dto;
    }

    public EmprestimoListDto devolver(int id) throws Exception {
        Emprestimo emprestimo = eRepo.findById(id).orElseThrow(() -> new Exception("Empréstimo não encontrado"));

        try {
            rt.patchForObject(URL_BOOK + "/" + emprestimo.getIsbnLivro() + "/devolver", null, BookSimpleListDto.class);
        } catch (Exception e) {
            throw new Exception("Falha ao registrar devolução no microsserviço de livros");
        }

        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimo = eRepo.save(emprestimo);

        return mapToListDto(emprestimo);
    }

    private EmprestimoListDto mapToListDto(Emprestimo e) {
        return new EmprestimoListDto(e.getId(), e.getIdUsuario(), e.getIsbnLivro(), e.getDataEmprestimo(), e.getDataDevolucao());
    }
}
