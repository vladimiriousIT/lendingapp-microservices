package com.peerlender.lendingengine;

import com.peerlender.lendingengine.domain.model.Balance;
import com.peerlender.lendingengine.domain.model.Currency;
import com.peerlender.lendingengine.domain.model.Money;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LendingengineApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(LendingengineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User vladi = new User("Vladi",
                "Vladi",
                "V",
                28,
                "Software Engineer", new Balance());

        User greti = new User("Greti", "Greti", "G", 28, "Accountant", new Balance());
        User milen = new User("Milen", "Milen", "M", 47, "Manager", new Balance());
        vladi.topUp(new Money(100, Currency.USD));
        greti.topUp(new Money(120, Currency.USD));
        milen.topUp(new Money(150, Currency.USD));
        userRepository.save(vladi);
        userRepository.save(greti);
        userRepository.save(milen);
    }
}
