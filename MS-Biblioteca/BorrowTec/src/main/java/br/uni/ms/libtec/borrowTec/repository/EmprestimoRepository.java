package br.uni.ms.libtec.borrowTec.repository;

import br.uni.ms.libtec.borrowTec.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
}
