package br.edu.uniacademia.ApostasBet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeResponseComDataDTO {
    private int id;
    private String nome;
    private LocalDate fundacao;
}
