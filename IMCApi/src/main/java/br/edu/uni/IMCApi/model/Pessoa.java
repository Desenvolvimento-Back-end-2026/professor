package br.edu.uni.IMCApi.model;


import java.time.LocalDate;
import java.util.ArrayList;

public class Pessoa {

    private String nome;
    private double peso,altura;
    private boolean sexo;
    private LocalDate dataNascimento;
    private ArrayList<Vacina> vacinasTomadas = new ArrayList<>();

    public Pessoa(String nome, double peso, double altura, boolean sexo, LocalDate dataNascimento) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        vacinasTomadas = new ArrayList<>();
    }
    public Pessoa() {
        vacinasTomadas = new ArrayList<>();
    }
    public Pessoa(String nome, double peso, double altura) {
        this.peso = peso;
        this.altura = altura;
        this.nome = nome;
        vacinasTomadas = new ArrayList<>();
    }

    public double imc(){
        return peso/(altura*altura);
    }

    public String faixa(){
        double imc = imc();
        if (imc <= 18) {
            return "Muito Magro";
        }else if (imc <= 25){
            return "Normal";
        }else if (imc <= 32){
            return "Gordo";
        }else {
            return "Imenso";
        }
    }

    public ArrayList<Vacina> getVacinasTomadas() {
        return vacinasTomadas;
    }

    public void setVacinasTomadas(ArrayList<Vacina> vacinasTomadas) {
        this.vacinasTomadas = vacinasTomadas;
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
