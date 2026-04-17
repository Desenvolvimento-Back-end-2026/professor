package br.edu.uniacademia.ApostasBet.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestaApiResource {

    @GetMapping("/public/versao")
    public ResponseEntity<?> getPublico(){
        return ResponseEntity.ok().body("API Pública :: Versão 1.0.0");
    }

    @GetMapping("/admin/info")
    public ResponseEntity<?> getAdmin(Authentication auth){
        return ResponseEntity.ok().body("API Privada :: "+auth.getName()
                +" :: Somente Administrador");
    }

    @GetMapping("/apostador/dados")
    public ResponseEntity<?> getApostador(){
        return ResponseEntity.ok().body("API Privada :: Somente Apostador ::" +
                " R$ 1.000.000.00 Arrecadado");
    }


    @GetMapping("/geral")
    @PreAuthorize("hasRole('APOSTADOR')")
    public ResponseEntity<?> getGeral(){
        return ResponseEntity.ok().body("API Privada :: Dados Gerais");
    }
}
