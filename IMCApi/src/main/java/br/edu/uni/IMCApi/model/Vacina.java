package br.edu.uni.IMCApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vacina {

    private String nome, fabricante;
    private double preco;
    private LocalDateTime dataVacina;

    public Vacina(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}
