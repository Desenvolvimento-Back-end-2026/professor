package br.edu.uniacademia.ApostasBet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotBlank
    @Column(nullable = false, length = 40,unique = true)
    private String nome;
    private LocalDate dataFundacao;

    @OneToMany(mappedBy = "timeMandante")
    private List<Jogo> jogosMandante;
    @OneToMany(mappedBy = "timeVisitante")
    private List<Jogo> jogosVisitante;



}
