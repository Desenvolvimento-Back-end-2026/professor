package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.dto.UserListDto;
import br.uni.ms.libtec.borrowTec.model.Usuario;
import br.uni.ms.libtec.borrowTec.repository.UsuarioRepository;
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
}
