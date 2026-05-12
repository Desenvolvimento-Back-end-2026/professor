package br.uni.ms.libtec.borrowTec.repository;

import br.uni.ms.libtec.borrowTec.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository
        extends JpaRepository<Genero, Integer> {
}
