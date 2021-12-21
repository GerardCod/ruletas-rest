package dev.gerardo.microservices.ruletas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RuletasApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuletasApplication.class, args);
	}

}
