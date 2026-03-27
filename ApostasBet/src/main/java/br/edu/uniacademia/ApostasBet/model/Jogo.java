package br.edu.uniacademia.ApostasBet.model;

import br.edu.uniacademia.ApostasBet.model.emn.EStatusJogo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Time timeMandante;
    private Time timeVisitante;

    private int golsTimeMandante, golsTimeVisitante;
    private double oddsTimeMandante, odssTimeVisitante, oddsEmpate;
    private LocalDateTime dataJogo;
    private EStatusJogo status;
}
