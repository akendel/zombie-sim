package de.obi.challenge.zombie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@ImportResource({"classpath:spring-integration.xml"})
public class ZombieAttackSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZombieAttackSimulatorApplication.class, args);
	}
}
