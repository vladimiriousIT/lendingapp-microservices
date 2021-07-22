package com.peerlender.lendingengine.application.service;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanApplicationAdapter {
    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest request, User borrower) {
        Optional<User> userOptional = userRepository.findById(borrower.getUsername());
        if(userOptional.isPresent()) {
            return new LoanApplication(request.getAmount(), userOptional.get(),
                    request.getDaysToRepay(), request.getInterestRate());
        } else{
            throw new UserNotFoundException(borrower.getUsername());
        }
    }
}
