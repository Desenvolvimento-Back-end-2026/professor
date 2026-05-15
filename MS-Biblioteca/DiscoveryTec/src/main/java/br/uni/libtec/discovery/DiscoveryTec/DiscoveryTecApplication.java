package br.uni.libtec.discovery.DiscoveryTec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryTecApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryTecApplication.class, args);
	}

}
