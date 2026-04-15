package br.edu.uniacademia.ApostasBet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(CampoVazioException.class)
    public ResponseEntity<Object> handleCampoVazioException(CampoVazioException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("mensagem", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<Object> handleDataInvalidaException(DataInvalidaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(RegraNegocioException.class)
//    public ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("mensagem", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(RecursoNaoEncontradoException.class)
//    public ResponseEntity<Object> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("mensagem", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage()));
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//


}
