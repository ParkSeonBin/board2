package com.example.board_maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardMavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardMavenApplication.class, args);
	}

}
