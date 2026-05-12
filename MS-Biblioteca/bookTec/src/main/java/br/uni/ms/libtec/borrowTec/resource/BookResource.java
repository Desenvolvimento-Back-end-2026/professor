package br.uni.ms.libtec.borrowTec.resource;

import br.uni.ms.libtec.borrowTec.dto.BookCreateDto;
import br.uni.ms.libtec.borrowTec.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookResource {

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBook(){
        try {
            return ResponseEntity.ok(bookService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getOneBook(@PathVariable("isbn") String isbn){
        try {
            return ResponseEntity.ok(bookService.getOne(isbn));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookCreateDto dto){
        try {
            return ResponseEntity.status(201).body(bookService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> editBook(@PathVariable("isbn") String isbn, @RequestBody @Valid BookCreateDto dto){
        try {
            return ResponseEntity.ok(bookService.edit(isbn, dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn") String isbn){
        try {
            return ResponseEntity.ok(bookService.delete(isbn));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{isbn}/emprestar")
    public ResponseEntity<?> emprestar(@PathVariable("isbn") String isbn){
        try {
            bookService.emprestar(isbn);
            return ResponseEntity.ok("Empréstimo registrado no livro com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{isbn}/devolver")
    public ResponseEntity<?> devolver(@PathVariable("isbn") String isbn){
        try {
            bookService.devolver(isbn);
            return ResponseEntity.ok("Devolução registrada no livro com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
