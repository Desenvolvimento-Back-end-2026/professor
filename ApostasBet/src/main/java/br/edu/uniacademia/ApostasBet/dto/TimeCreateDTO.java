package br.edu.uniacademia.ApostasBet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeCreateDTO {

    private String nome;
    private LocalDate dataFundacao;
}
