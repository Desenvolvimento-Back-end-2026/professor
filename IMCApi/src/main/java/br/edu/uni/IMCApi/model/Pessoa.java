package br.edu.uni.IMCApi.model;


import java.time.LocalDate;

public class Pessoa {

    private String nome;
    private double peso,altura;
    private boolean sexo;
    private LocalDate dataNascimento;

    public Pessoa(String nome, double peso, double altura, boolean sexo, LocalDate dataNascimento) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }
    public Pessoa() {
    }
    public Pessoa(String nome, double peso, double altura) {
        this.peso = peso;
        this.altura = altura;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
