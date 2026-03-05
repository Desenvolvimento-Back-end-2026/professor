package br.edu.uni.IMCApi;

import br.edu.uni.IMCApi.model.Pessoa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImcApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ImcApiApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        System.out.println("SERVER RODANDO");
        Pessoa p1 = new Pessoa("Zezin da Silva ", 180, 1.40 );



    }
}
