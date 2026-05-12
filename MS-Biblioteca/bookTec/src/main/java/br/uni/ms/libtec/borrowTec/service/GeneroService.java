package br.uni.ms.libtec.borrowTec.service;

import br.uni.ms.libtec.borrowTec.dto.GeneroDto;
import br.uni.ms.libtec.borrowTec.model.Genero;
import br.uni.ms.libtec.borrowTec.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    public List<GeneroDto> getAll() throws Exception {
        List<Genero> generos = generoRepository.findAll();
        if (generos.isEmpty()) {
            throw new Exception("Não há gêneros cadastrados");
        }
        return generos.stream()
                .map(g -> new GeneroDto(g.getId(), g.getNome()))
                .collect(Collectors.toList());
    }

    public GeneroDto getOne(int id) {
        Genero genero = generoRepository.findById(id).orElseThrow();
        return new GeneroDto(genero.getId(), genero.getNome());
    }

    public GeneroDto save(GeneroDto generoDto) {
        Genero genero = new Genero();
        genero.setNome(generoDto.getNome());
        genero = generoRepository.save(genero);
        return new GeneroDto(genero.getId(), genero.getNome());
    }

    public GeneroDto edit(int id, GeneroDto generoDto) {
        Genero genero = generoRepository.findById(id).orElseThrow();
        genero.setNome(generoDto.getNome());
        genero = generoRepository.save(genero);
        return new GeneroDto(genero.getId(), genero.getNome());
    }

    public GeneroDto delete(int id) {
        Genero genero = generoRepository.findById(id).orElseThrow();
        GeneroDto dto = new GeneroDto(genero.getId(), genero.getNome());
        generoRepository.delete(genero);
        return dto;
    }
}
