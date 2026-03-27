package br.edu.uniacademia.ApostasBet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Administrador extends Usuario {

    @Column(nullable = false, unique = true, length = 14)
    @NotNull
    @NotBlank
    private String matricula;


    public Administrador(int id, String nome, String email, String login, String senha, LocalDateTime dataUltimoAcesso, String matricula) {
        super(id, nome, email, login, senha, dataUltimoAcesso);
        this.matricula = matricula;
    }
}
