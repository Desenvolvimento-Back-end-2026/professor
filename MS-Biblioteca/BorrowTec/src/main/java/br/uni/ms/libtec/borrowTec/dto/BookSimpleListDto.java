package br.uni.ms.libtec.borrowTec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSimpleListDto {
    private String isbn;
    private String titulo;
    private int numeroExemplares;
    private int numeroEmprestado;
}
