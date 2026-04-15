package br.edu.uniacademia.ApostasBet.exceptions;

public class CampoVazioException extends Exception {

    public CampoVazioException(String nome) {
        super(nome+" não pode ser vazio!!!");
    }

}
