package br.uni.ms.libtec.borrowTec.resource;

import br.uni.ms.libtec.borrowTec.dto.EmprestimoCreateDto;
import br.uni.ms.libtec.borrowTec.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emprestimo")
public class EmprestimoResource {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(emprestimoService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(emprestimoService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid EmprestimoCreateDto dto) {
        try {
            return ResponseEntity.status(201).body(emprestimoService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody @Valid EmprestimoCreateDto dto) {
        try {
            return ResponseEntity.ok(emprestimoService.edit(id, dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(emprestimoService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(emprestimoService.devolver(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
