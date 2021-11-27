package com.lanoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LanoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanoaApplication.class, args);
	}

}
