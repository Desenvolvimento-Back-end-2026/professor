package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.dto.UserCreateDTO;
import br.uni.ms.libtec.borrowTec.dto.UserListDto;
import br.uni.ms.libtec.borrowTec.model.Usuario;
import br.uni.ms.libtec.borrowTec.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository uRepo;

    public List<UserListDto> getAll() throws Exception {
        List<Usuario> usuarios = uRepo.findAll();

        if (usuarios.isEmpty()) {
            throw new Exception("Não há usuários cadastrados");
        }
        List<UserListDto> lista = new ArrayList<>();

        usuarios.forEach( u ->{
            lista.add(
                    new UserListDto(u.getId(),
                    u.getNome(),
                    (u.isEhAdministrador()?"Administrador":"Leitor"))
            );
        });

        return lista;
    }

    public UserListDto getOne(int id) {
        Usuario usuario = uRepo.findById(id).orElseThrow();

        return new UserListDto(usuario.getId(), usuario.getNome(),
                (usuario.isEhAdministrador()?"Administrador":"Leitor") );

    }

    public UserListDto save(@Valid UserCreateDTO user) throws Exception {
        //TO-DO: Criptografar a senha ao Salvar
        Usuario u = new Usuario(0, user.getNome(),
                user.getLogin(), user.getSenha(),
                user.isEhAdministrador());
        if (u.getLogin().length() <= 7) {
            throw new Exception("Login deve ter mais de 8 caracteres");
        }
        if (u.getSenha().length() <= 7) {
            throw new Exception("A senha deve ter mais de 8 caracteres");
        }
        if (!u.getSenha().matches(".*[a-z].*") ) {
            throw new Exception("A senha deve possuir letras minusculas");
        }
        if (!u.getSenha().matches(".*[A-Z].*") ) {
            throw new Exception("A senha deve possuir letras Maiusculas");
        }
        if (!u.getSenha().matches(".*[0-9].*") ) {
            throw new Exception("A senha deve possuir numeros");
        }

        u = uRepo.save(u);

        return new UserListDto(u.getId(), u.getNome(),
                (u.isEhAdministrador()?"Administrador":"Leitor") );
    }

    public UserListDto delete(int id) {
        Usuario usuario = uRepo.findById(id).orElseThrow();
        UserListDto u =new UserListDto(usuario.getId(), usuario.getNome(),
                (usuario.isEhAdministrador()?"Administrador":"Leitor") );
        uRepo.delete(usuario);
        return u;
    }

    public UserListDto edit(int id, @Valid UserCreateDTO user) {
        Usuario usuario = uRepo.findById(id).orElseThrow();
        usuario.setNome(user.getNome());
        usuario.setLogin(user.getLogin());

        usuario = uRepo.save(usuario);

        return new UserListDto(usuario.getId(), usuario.getNome(),
                (usuario.isEhAdministrador()?"Administrador":"Leitor") );
    }
}
