package br.edu.uniacademia.ApostasBet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "apostador")
    private List<Aposta> apostas;

    public Apostador(int id, String nome, String email,
                     String login, String senha, LocalDateTime dt,
                     double saldo,
                     LocalDate nascimento, String cpf, String conta,
                     boolean bloqueado) {
        super(id, nome, email,login, senha, dt);
        this.saldo = saldo;
        this.dataNascimento = nascimento;
        this.cpf = cpf;
        this.contaBancaria = conta;
        this.bloqueado = bloqueado;
    }
}
