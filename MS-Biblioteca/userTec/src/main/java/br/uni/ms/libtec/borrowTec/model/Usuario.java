package br.uni.ms.libtec.borrowTec.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @Column(length = 100, nullable = false)
    private String senha;

    private boolean ehAdministrador;
}
