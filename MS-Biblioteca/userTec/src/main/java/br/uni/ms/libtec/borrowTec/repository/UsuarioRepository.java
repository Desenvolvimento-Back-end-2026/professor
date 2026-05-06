package br.uni.ms.libtec.borrowTec.repository;

import br.uni.ms.libtec.borrowTec.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {
}
