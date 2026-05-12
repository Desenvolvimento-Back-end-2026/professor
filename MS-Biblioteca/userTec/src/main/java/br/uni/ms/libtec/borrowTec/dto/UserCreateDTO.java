package br.uni.ms.libtec.borrowTec.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    private String nome, login;

    @NotNull
    @NotBlank
    @NotEmpty
    private String senha;
    private boolean ehAdministrador;
}
