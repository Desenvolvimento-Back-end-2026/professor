package br.uni.ms.libtec.borrowTec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
    private int id;
    private String nome;
    private String tipoUsuario;
}
