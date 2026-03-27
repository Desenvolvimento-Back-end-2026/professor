package br.edu.uniacademia.ApostasBet;

import br.edu.uniacademia.ApostasBet.model.Administrador;
import br.edu.uniacademia.ApostasBet.model.Time;
import br.edu.uniacademia.ApostasBet.repository.AdminRepository;
import br.edu.uniacademia.ApostasBet.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	@Override
	public void run(String... args) throws Exception {
		Time campeao = new Time(0, "São Paulo",
				LocalDate.now().minusYears(98));

		timeRepo.save(campeao);
		timeRepo.flush();

		Time t1 = timeRepo.findByNome("São Paulo");
		System.out.println(t1.getNome());

		Administrador adm = new Administrador(0,"Admin José",
				"admin@unibet.com.br","admin","admin",
				null,"123");

		adminRepo.save(adm);
		adminRepo.flush();
	}
}
