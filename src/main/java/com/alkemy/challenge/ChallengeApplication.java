package com.alkemy.challenge;

import com.alkemy.challenge.security.entity.Role;
import com.alkemy.challenge.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import static com.alkemy.challenge.security.enums.ERole.ROLE_ADMIN;
import static com.alkemy.challenge.security.enums.ERole.ROLE_USER;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ChallengeApplication.class, Jsr310JpaConverters.class})
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args -> {
			roleRepository.save(new Role(null, ROLE_USER));
			roleRepository.save(new Role(null, ROLE_ADMIN));
		};
	}
}
