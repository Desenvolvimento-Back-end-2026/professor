package br.edu.uniacademia.ApostasBet.repository;

import br.edu.uniacademia.ApostasBet.model.Aposta;
import br.edu.uniacademia.ApostasBet.model.Apostador;
import br.edu.uniacademia.ApostasBet.model.emn.EStatusAposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ApostadorRepository extends JpaRepository<Apostador, Integer> {

    public boolean existsByNomeAndDataNascimentoLessThan(String nome, LocalDate data);

    public List<Apostador> findByBloqueadoTrue();

    public List<Apostador> findByNomeAndApostasStatus(String nome, EStatusAposta status);

    public List<Apostador> findByApostasStatus( EStatusAposta status);

    public List<Apostador> findByContaBancariaEmpty();

    //JQPL - JPA QUERY LANGUAGE
    // select * from APOSTADOR
    @Query("select a from Apostador a Where a.nome like :nm")
    public List<Apostador> busca(String nm);

    public List<Apostador> findByNomeLike(String nm);


    @Query("select ap from Apostador a join a.apostas ap " +
            "where a.id = :id and ap.status = EStatusAposta.PREMIADA")
    public List<Aposta> buscaApostasGanhas(int id);


}
