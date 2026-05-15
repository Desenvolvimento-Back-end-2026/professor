package br.uni.ms.libtec.borrowTec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookTecApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookTecApplication.class, args);
	}

}
