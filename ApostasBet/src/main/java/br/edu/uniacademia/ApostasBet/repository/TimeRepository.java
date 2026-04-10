package br.edu.uniacademia.ApostasBet.repository;

import br.edu.uniacademia.ApostasBet.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface TimeRepository
        extends JpaRepository<Time, Integer> {

    public Time findByNome(String nome);

    public List<Time> findByNomeLike(String nome);

    public List<Time> findByDataFundacaoLessThan(LocalDate dt);

    public List<Time> findByJogosMandanteGolsTimeMandanteGreaterThan(int gols);

}
