package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.dto.BookCreateDto;
import br.uni.ms.libtec.borrowTec.dto.BookListDto;
import br.uni.ms.libtec.borrowTec.dto.BookSimpleListDto;
import br.uni.ms.libtec.borrowTec.model.Book;
import br.uni.ms.libtec.borrowTec.model.Genero;
import br.uni.ms.libtec.borrowTec.repository.BookRepository;
import br.uni.ms.libtec.borrowTec.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bRepo;

    @Autowired
    GeneroRepository gRepo;

    public List<BookListDto> getAll() throws Exception {
        List<Book> books = bRepo.findAll();

        if (books.isEmpty()) {
            throw new Exception("Não há livros cadastrados");
        }
        List<BookListDto> lista = new ArrayList<>();

        books.forEach( b ->{
            lista.add(
                    new BookListDto(b.getIsbn(),
                            b.getTitulo(),b.getAutor(),
                            b.getEditora(), b.getColecao(),
                            b.getAnoLancamento(),b.getNumeroExemplares(),
                            b.getNumeroEmprestado(),
                            b.getGenero() != null ? b.getGenero().getNome() : null)
            );
        });

        return lista;
    }

    public BookListDto getOne(String isbn) {
        Book b = bRepo.findById(isbn).orElseThrow();
        return new BookListDto(b.getIsbn(),
                b.getTitulo(), b.getAutor(),
                b.getEditora(), b.getColecao(),
                b.getAnoLancamento(), b.getNumeroExemplares(),
                b.getNumeroEmprestado(),
                b.getGenero() != null ? b.getGenero().getNome() : null);
    }

    public BookListDto save(BookCreateDto dto) {
        Genero genero = gRepo.findById(dto.getGeneroId()).orElseThrow();
        
        Book b = new Book();
        b.setIsbn(dto.getIsbn());
        b.setTitulo(dto.getTitulo());
        b.setAutor(dto.getAutor());
        b.setEditora(dto.getEditora());
        b.setColecao(dto.getColecao());
        b.setAnoLancamento(dto.getAnoLancamento());
        b.setNumeroExemplares(dto.getNumeroExemplares());
        b.setNumeroEmprestado(0); // Inicia com 0
        b.setGenero(genero);

        b = bRepo.save(b);

        return new BookListDto(b.getIsbn(), b.getTitulo(), b.getAutor(),
                b.getEditora(), b.getColecao(), b.getAnoLancamento(),
                b.getNumeroExemplares(), b.getNumeroEmprestado(),
                b.getGenero().getNome());
    }

    public BookListDto edit(String isbn, BookCreateDto dto) {
        Book b = bRepo.findById(isbn).orElseThrow();
        Genero genero = gRepo.findById(dto.getGeneroId()).orElseThrow();

        b.setTitulo(dto.getTitulo());
        b.setAutor(dto.getAutor());
        b.setEditora(dto.getEditora());
        b.setColecao(dto.getColecao());
        b.setAnoLancamento(dto.getAnoLancamento());
        b.setNumeroExemplares(dto.getNumeroExemplares());
        b.setGenero(genero);

        b = bRepo.save(b);

        return new BookListDto(b.getIsbn(), b.getTitulo(), b.getAutor(),
                b.getEditora(), b.getColecao(), b.getAnoLancamento(),
                b.getNumeroExemplares(), b.getNumeroEmprestado(),
                b.getGenero().getNome());
    }

    public BookListDto delete(String isbn) {
        Book b = bRepo.findById(isbn).orElseThrow();
        BookListDto dto = new BookListDto(b.getIsbn(), b.getTitulo(), b.getAutor(),
                b.getEditora(), b.getColecao(), b.getAnoLancamento(),
                b.getNumeroExemplares(), b.getNumeroEmprestado(),
                b.getGenero() != null ? b.getGenero().getNome() : null);
        bRepo.delete(b);
        return dto;
    }

    public BookSimpleListDto emprestar(String isbn) throws Exception {

        Book b = bRepo.findById(isbn).orElseThrow( );

        if (b.getNumeroExemplares() <= 0){
            throw new Exception("não há mais livros para emprestimo!!!");
        }

        b.setNumeroExemplares( b.getNumeroExemplares() -1 );
        b.setNumeroEmprestado( b.getNumeroEmprestado() +1 );

        bRepo.save(b);

        return new BookSimpleListDto(b.getIsbn(),
                b.getTitulo(), b.getNumeroExemplares(),
                b.getNumeroEmprestado());


    }

    public BookSimpleListDto devolver(String isbn) throws Exception {
        Book b = bRepo.findById(isbn).orElseThrow( );

        if (b.getNumeroEmprestado() <= 0){
            throw new Exception("não há mais livros emprestados!!!");
        }


        b.setNumeroExemplares( b.getNumeroExemplares() +1 );
        b.setNumeroEmprestado( b.getNumeroEmprestado() -1 );

        bRepo.save(b);

        return new BookSimpleListDto(b.getIsbn(),
                b.getTitulo(), b.getNumeroExemplares(),
                b.getNumeroEmprestado());

    }
}
