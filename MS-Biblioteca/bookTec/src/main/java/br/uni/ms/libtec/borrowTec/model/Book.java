package br.uni.ms.libtec.borrowTec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    private String isbn;

    @Column(length = 100, nullable = false, unique = true)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String autor;
    @Column(length = 100, nullable = false)
    private String editora;
    @Column(length = 100, nullable = false)
    private String colecao;

    @Column( nullable = false)
    private int anoLancamento;

    private int numeroExemplares;
    private int numeroEmprestado;

    @ManyToOne()
    private Genero genero;

}
