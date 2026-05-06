package br.uni.ms.libtec.borrowTec.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private int id;
    private String nome;
    private String login;
    private String senha;
    private boolean ehAdministrador;

}
