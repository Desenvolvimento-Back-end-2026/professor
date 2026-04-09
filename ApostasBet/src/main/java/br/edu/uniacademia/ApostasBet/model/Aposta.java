package br.edu.uniacademia.ApostasBet.model;

import br.edu.uniacademia.ApostasBet.model.emn.EEscolha;
import br.edu.uniacademia.ApostasBet.model.emn.EStatusAposta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Jogo jogo;

    @ManyToOne
    private Apostador apostador;

    private double valorAposta;
    private int escolhaGolsTimeMandante, escolhaGolsTimeVisitante;
    @Enumerated(EnumType.STRING)
    private EEscolha escolha;
    private double premioGanho;
    @Enumerated(EnumType.STRING)
    private EStatusAposta status;


}
