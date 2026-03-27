package br.edu.uniacademia.ApostasBet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Apostador extends Usuario {

    @Column(nullable = false)
    private double saldo;
    @Past
    @Temporal(TemporalType.DATE)
    private LocalDate dataNascimento;
    @Column(nullable = false, length = 14, unique = true)
    @NotNull
    @NotBlank
    private String cpf;
    @Column(nullable = false, length = 14)
    @NotNull
    @NotBlank
    private String contaBancaria;
    private boolean bloqueado;

}
