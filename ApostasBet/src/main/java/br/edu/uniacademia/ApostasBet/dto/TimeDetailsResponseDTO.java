package br.edu.uniacademia.ApostasBet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDetailsResponseDTO {

    private Integer id;
    private String nome;
    private LocalDate dataFundacao;
    private int numeroJogosVisitante, numeroJogosMandante;

}
