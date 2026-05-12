package br.uni.ms.libtec.borrowTec.resource;


import br.uni.ms.libtec.borrowTec.dto.UserCreateDTO;
import br.uni.ms.libtec.borrowTec.dto.UserListDto;
import br.uni.ms.libtec.borrowTec.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UsuarioResource {

    @Autowired
    UsuarioService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(userService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserCreateDTO user){
        try {
            UserListDto u = userService.save(user);
            return ResponseEntity.status(201).body(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") int id,
                                      @RequestBody @Valid UserCreateDTO user){
        try {
            return ResponseEntity.ok(userService.edit(id, user));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
