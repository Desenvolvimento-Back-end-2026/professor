package br.edu.uniacademia.ApostasBet.model;

import br.edu.uniacademia.ApostasBet.model.emn.EStatusJogo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Time timeMandante;
    @ManyToOne
    private Time timeVisitante;

    private int golsTimeMandante, golsTimeVisitante;
    private double oddsTimeMandante, odssTimeVisitante, oddsEmpate;
    private LocalDateTime dataJogo;
    private EStatusJogo status;

    @OneToMany(mappedBy = "jogo")
    private List<Aposta> apostas;
}
