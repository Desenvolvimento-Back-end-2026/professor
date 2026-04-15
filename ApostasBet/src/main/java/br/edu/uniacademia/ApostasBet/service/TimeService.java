package br.edu.uniacademia.ApostasBet.service;

import br.edu.uniacademia.ApostasBet.dto.TimeCreateDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeDetailsResponseDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeResponseComDataDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeResponseDTO;
import br.edu.uniacademia.ApostasBet.exceptions.CampoVazioException;
import br.edu.uniacademia.ApostasBet.exceptions.DataInvalidaException;
import br.edu.uniacademia.ApostasBet.model.Time;
import br.edu.uniacademia.ApostasBet.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeService {

    @Autowired
    TimeRepository timeRepository;

    public TimeResponseDTO salvar(TimeCreateDTO time)
            throws CampoVazioException, DataInvalidaException {
        Time t = new Time(0, time.getNome(), time.getDataFundacao()
                ,null,null);

        if (t.getNome().isEmpty()){
            throw new CampoVazioException("Nome");
        }
        if (t.getDataFundacao()==null){
            throw new CampoVazioException("");
        }
        if (t.getDataFundacao().isAfter(LocalDate.now())){
            throw new DataInvalidaException("Data de Fundação não pode ser superior " +
                    "a "+LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

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

    public TimeResponseDTO alterar(Integer id, TimeCreateDTO time)
            throws CampoVazioException {
        Optional<Time> tOp = timeRepository.findById(id);
        if (tOp.isPresent()){
            Time t = tOp.get();
            t.setNome(time.getNome());
            t.setDataFundacao(time.getDataFundacao());

            if (t.getNome().isEmpty()){
                throw new CampoVazioException("Nome");
            }

            timeRepository.save(t);
            return new TimeResponseDTO(t.getId(), t.getNome());
        }
        return null;
    }

    public List<TimeResponseComDataDTO> getTimes100Anos() {
        int ano100 = LocalDate.now().getYear() - 100;

        LocalDate data100AnosAtras = LocalDate.of(ano100,
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());

        List<Time> timeList = timeRepository.findByDataFundacaoLessThan(data100AnosAtras);

        List<TimeResponseComDataDTO> lista = new ArrayList<>();
        for (Time time : timeList) {
            lista.add( new TimeResponseComDataDTO(time.getId(),
                    time.getNome(), time.getDataFundacao()));
        }
        return lista;
    }
}
