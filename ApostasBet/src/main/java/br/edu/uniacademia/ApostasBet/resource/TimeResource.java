package br.edu.uniacademia.ApostasBet.resource;


import br.edu.uniacademia.ApostasBet.dto.TimeCreateDTO;
import br.edu.uniacademia.ApostasBet.dto.TimeDetailsResponseDTO;
import br.edu.uniacademia.ApostasBet.exceptions.CampoVazioException;
import br.edu.uniacademia.ApostasBet.exceptions.DataInvalidaException;
import br.edu.uniacademia.ApostasBet.model.Time;
import br.edu.uniacademia.ApostasBet.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time")
public class TimeResource {

    @Autowired
    TimeService timeService;

    @PostMapping()
    public ResponseEntity<?> saveTime( @RequestBody TimeCreateDTO time)
            throws CampoVazioException, DataInvalidaException {
//        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body( timeService.salvar(time)  );
//        } catch (CampoVazioException | DataInvalidaException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body( timeService.getAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id ){
        TimeDetailsResponseDTO t = timeService.get(id);
        if(t != null){
            return ResponseEntity.ok().body( t );
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable Integer id ){
        if (timeService.apagar(id)){
            return ResponseEntity.ok().body( "Time apagado com sucesso" );
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Integer id,
                                     @RequestBody TimeCreateDTO time)
            throws CampoVazioException {

        return ResponseEntity.ok().body( timeService.alterar(id, time));

    }

    @GetMapping("/centenarios")
    public ResponseEntity<?> getTimeCentenarios(){
        return ResponseEntity.ok().body( timeService.getTimes100Anos() );
    }


}
