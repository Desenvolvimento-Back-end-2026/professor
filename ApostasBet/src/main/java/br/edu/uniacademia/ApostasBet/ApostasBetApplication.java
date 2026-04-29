package br.edu.uniacademia.ApostasBet;

import br.edu.uniacademia.ApostasBet.model.*;
import br.edu.uniacademia.ApostasBet.model.emn.EEscolha;
import br.edu.uniacademia.ApostasBet.model.emn.EStatusAposta;
import br.edu.uniacademia.ApostasBet.model.emn.EStatusJogo;
import br.edu.uniacademia.ApostasBet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class ApostasBetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApostasBetApplication.class, args);
	}


	@Autowired
	TimeRepository timeRepo;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	ApostadorRepository apostadorRepo;
	@Autowired
	JogoRepositpry jogoRepo;
	@Autowired
	ApostaRepository apostaRepo;
	@Autowired
	PasswordEncoder b;

	@Override
	public void run(String... args) throws Exception {
		Time campeao = new Time(0, "São Paulo",
				LocalDate.now().minusYears(98),
				null, null);
		Time campeao1 = new Time(0, "Flamengo",
				LocalDate.now().minusYears(50),
				null, null);

		timeRepo.save(campeao);
		timeRepo.save(campeao1);
		timeRepo.flush();

		Time t1 = timeRepo.findByNome("São Paulo");
		System.out.println(t1.getNome());

		Administrador adm = new Administrador(0,"Admin José",
				"admin@unibet.com.br","admin",b.encode("admin"),
				null,"123");

		adminRepo.save(adm);
		adminRepo.flush();

		Apostador apo = new Apostador(0, "Pedrin da Silva",
				"pedrin@pedrin","ped",b.encode("123"), null,1000,
				LocalDate.now().minusYears(25),
				"123456789","7070-x",false);

		apostadorRepo.save(apo);
		apostadorRepo.flush();

		Jogo jg = new Jogo(0,campeao,campeao1,
				7,1,
				0.58,0.9,0.2,
				null, EStatusJogo.CONCLUIDA,null);

		jogoRepo.save(jg);
		jogoRepo.flush();


		Aposta apt = new Aposta(0,jg,apo,500,
				5,0,
				EEscolha.MANDANTE,1500,
				EStatusAposta.PREMIADA);

		apostaRepo.save(apt);
		apostaRepo.flush();


		//BCryptPasswordEncoder b = new BCryptPasswordEncoder();

		System.out.println(b.encode("Spring boot"));
		System.out.println(b.encode("Spring Boot"));
		System.out.println(b.encode("a"));
		System.out.println(b.encode("sdfkj ashdfkjsh jghdfsjf hsdjf hsdjjdsf jkdsh"));

	}
}
