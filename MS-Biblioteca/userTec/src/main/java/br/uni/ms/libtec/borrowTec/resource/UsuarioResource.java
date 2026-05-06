package br.uni.ms.libtec.borrowTec.resource;


import br.uni.ms.libtec.borrowTec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/user")
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

}
