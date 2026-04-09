package br.edu.uniacademia.ApostasBet.repository;

import br.edu.uniacademia.ApostasBet.model.Aposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApostaRepository extends
        JpaRepository<Aposta, Integer> {


}
