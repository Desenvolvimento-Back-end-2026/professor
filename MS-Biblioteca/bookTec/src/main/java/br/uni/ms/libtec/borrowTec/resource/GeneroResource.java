package br.uni.ms.libtec.borrowTec.resource;

import br.uni.ms.libtec.borrowTec.dto.GeneroDto;
import br.uni.ms.libtec.borrowTec.service.GeneroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genero")
public class GeneroResource {

    @Autowired
    GeneroService generoService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(generoService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(generoService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid GeneroDto generoDto) {
        try {
            return ResponseEntity.status(201).body(generoService.save(generoDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody @Valid GeneroDto generoDto) {
        try {
            return ResponseEntity.ok(generoService.edit(id, generoDto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(generoService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
