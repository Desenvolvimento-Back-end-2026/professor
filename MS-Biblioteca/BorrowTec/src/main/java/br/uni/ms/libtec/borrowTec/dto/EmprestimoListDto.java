package br.uni.ms.libtec.borrowTec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoListDto {

    private int id;
    private int idUsuario;
    private String isbnLivro;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;

}
