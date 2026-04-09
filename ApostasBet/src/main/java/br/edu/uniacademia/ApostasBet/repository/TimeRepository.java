package br.edu.uniacademia.ApostasBet.repository;

import br.edu.uniacademia.ApostasBet.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TimeRepository
        extends JpaRepository<Time, Integer> {

    public Time findByNome(String nome);

}
