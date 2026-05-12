package br.uni.ms.libtec.borrowTec.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;

    private int idUsuario;
    private String isbnLivro;

    @Transient
    private Book livro;
    @Transient
    private Usuario usuario;

}
