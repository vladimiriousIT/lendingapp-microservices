package com.peerLending.profile;

import com.peerLending.profile.domain.model.User;
import com.peerLending.profile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ProfileApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProfileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("Vladi", "Vladimir", "Strati", 28,
				"Software Engineer", LocalDate.now()));
		userRepository.save(new User("Greti", "Greti", "Yordanova", 28,
				"Accountant", LocalDate.now()));
	}
}
