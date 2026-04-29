package br.edu.uniacademia.ApostasBet.repository;

import br.edu.uniacademia.ApostasBet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByLogin(String login);

}
