package br.uni.ms.libtec.borrowTec.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroDto {

    private int id;

    @NotBlank(message = "O nome do gênero não pode ser vazio")
    private String nome;
}
