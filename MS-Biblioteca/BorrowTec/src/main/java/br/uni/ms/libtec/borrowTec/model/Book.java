package br.uni.ms.libtec.borrowTec.model;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String isbn;
    private String titulo;
    private String autor;
    private String editora;
    private String colecao;
    private int anoLancamento;
    private int numeroExemplares;
    private int numeroEmprestado;

    private Genero genero;
}
