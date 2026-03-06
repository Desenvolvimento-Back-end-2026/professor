package br.edu.uni.IMCApi;

import br.edu.uni.IMCApi.model.Pessoa;
import br.edu.uni.IMCApi.model.Vacina;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;

@SpringBootApplication
public class ImcApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ImcApiApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        System.out.println("SERVER RODANDO");
        Pessoa p1 = new Pessoa("Zezin da Silva ", 180, 1.40 );
        Vacina v1 = new Vacina("CoronaVac", 15.00);

        p1.getVacinasTomadas().add(v1);

        DecimalFormat df = new DecimalFormat("#0.00");

        System.out.println("nome: "+ p1.getNome());
        System.out.println("imc: "+ df.format(p1.imc()) );
        System.out.println("Faixa: "+ p1.faixa());
        System.out.println("Vacina "+v1.getNome());
        System.out.println("Vacinas tomadas "+p1.getVacinasTomadas().size());

    }
}
