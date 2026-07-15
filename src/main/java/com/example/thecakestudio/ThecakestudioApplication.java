package com.example.thecakestudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThecakestudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThecakestudioApplication.class, args);
	}

}
