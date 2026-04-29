package br.edu.uniacademia.ApostasBet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,length = 80)
    @NotNull
    @NotBlank
    private String nome;
    @Column(nullable = false,length = 80, unique = true)
    @NotNull
    @NotBlank
    private String email;
    @Column(nullable = false,length = 18, unique = true)
    @NotNull
    @NotBlank
    private String login;
    @Column(nullable = false,length = 60)
    @NotNull
    @NotBlank
    private String senha;
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataUltimoAcesso;

}
