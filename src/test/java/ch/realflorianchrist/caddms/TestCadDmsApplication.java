package ch.realflorianchrist.caddms;

import org.springframework.boot.SpringApplication;

public class TestCadDmsApplication {

	public static void main(String[] args) {
		SpringApplication.from(CadDmsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
