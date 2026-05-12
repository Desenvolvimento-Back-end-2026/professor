package br.uni.ms.libtec.borrowTec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {

    @NotBlank(message = "O ISBN não pode ser vazio")
    private String isbn;

    @NotBlank(message = "O título não pode ser vazio")
    private String titulo;

    @NotBlank(message = "O autor não pode ser vazio")
    private String autor;

    @NotBlank(message = "A editora não pode ser vazio")
    private String editora;

    @NotBlank(message = "A coleção não pode ser vazio")
    private String colecao;

    @NotNull(message = "O ano de lançamento é obrigatório")
    private Integer anoLancamento;

    @NotNull(message = "O número de exemplares é obrigatório")
    private Integer numeroExemplares;

    @NotNull(message = "O ID do gênero é obrigatório")
    private Integer generoId;
}
