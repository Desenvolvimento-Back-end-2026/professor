package br.uni.ms.libtec.borrowTec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoCreateDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotBlank(message = "O ISBN do livro é obrigatório")
    private String isbnLivro;

    //@NotNull(message = "A data de devolução prevista é obrigatória")
    private LocalDateTime dataDevolucao;
}
