package br.edu.uniacademia.ApostasBet.service;

import br.edu.uniacademia.ApostasBet.dto.TimeCreateDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeDetailsResponseDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeResponseDTO;
import br.edu.uniacademia.ApostasBet.model.Time;
import br.edu.uniacademia.ApostasBet.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeService {

    @Autowired
    TimeRepository timeRepository;

    public TimeResponseDTO salvar(TimeCreateDTO time) {
        Time t = new Time(0, time.getNome(), time.getDataFundacao()
                ,null,null);
        timeRepository.save(t);

        return new TimeResponseDTO(t.getId(), t.getNome());

    }

    public List<TimeResponseDTO> getAll() {
        List<Time> timeList = timeRepository.findAll();
        List<TimeResponseDTO> timeResponseDTOList = new ArrayList<>();
        for (Time time : timeList) {
            TimeResponseDTO tDTO = new TimeResponseDTO(time.getId(), time.getNome());
            timeResponseDTOList.add(tDTO);
        }
        return timeResponseDTOList;
    }

    public TimeDetailsResponseDTO get(Integer id) {
        Optional<Time> tOp = timeRepository.findById(id);
        if (tOp.isPresent()){
            Time t = tOp.get();
            TimeDetailsResponseDTO tdto = new TimeDetailsResponseDTO(t.getId(),
                    t.getNome(), t.getDataFundacao(),
                    t.getJogosVisitante().size(),
                    t.getJogosMandante().size());
            return tdto;
        }else{
            return null;
        }
    }

    public boolean apagar(Integer id) {
        Optional<Time> tOp = timeRepository.findById(id);
        if (tOp.isPresent()){
            timeRepository.delete(tOp.get());
            return true;
        }
        return false;
    }

    public TimeResponseDTO alterar(Integer id, TimeCreateDTO time) {
        Optional<Time> tOp = timeRepository.findById(id);
        if (tOp.isPresent()){
            Time t = tOp.get();
            t.setNome(time.getNome());
            t.setDataFundacao(time.getDataFundacao());
            timeRepository.save(t);
            return new TimeResponseDTO(t.getId(), t.getNome());
        }
        return null;
    }
}
