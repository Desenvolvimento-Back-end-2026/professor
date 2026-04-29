package br.edu.uniacademia.ApostasBet.security;


import br.edu.uniacademia.ApostasBet.model.Usuario;
import br.edu.uniacademia.ApostasBet.repository.UsuarioRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustonUserDetailService implements UserDetailsService {

    @Autowired
    UsuarioRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = userRepo.findByLogin(username);

        if (u == null){
            throw new UsernameNotFoundException("Login ou Senha incorreta!!!");
        }
        UserDetails ud = new UserLogado(u);

        return ud;
    }
}
