package br.edu.uniacademia.ApostasBet.dto;

import br.edu.uniacademia.ApostasBet.model.Time;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeResponseDTO{

    private int id;
    private String nome;

}
