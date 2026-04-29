package br.edu.uniacademia.ApostasBet.security;

import br.edu.uniacademia.ApostasBet.model.Administrador;
import br.edu.uniacademia.ApostasBet.model.Usuario;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserLogado implements UserDetails {

    private Usuario user;

    public UserLogado(Usuario u) {
        user = u;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> lista = new ArrayList<>();
        lista.add(new SimpleGrantedAuthority("USER"));
        if (user instanceof Administrador){
            lista.add(new SimpleGrantedAuthority("ADMIN"));
        }else{
            lista.add(new SimpleGrantedAuthority("APOSTADOR"));
        }
        return lista;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }
}
