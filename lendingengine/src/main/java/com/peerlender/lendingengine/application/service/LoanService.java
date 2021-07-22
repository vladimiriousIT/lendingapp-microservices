package com.peerlender.lendingengine.application.service;

import com.peerlender.lendingengine.domain.exception.LoanApplicationNotFoundException;
import com.peerlender.lendingengine.domain.exception.LoanNotFoundException;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.*;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.LoanRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class LoanService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public void repayLoan(final Money amountToRepay,
                          final long loanId,
                          final User borrower) {
        Loan loan = loanRepository.findOneByIdAndBorrower(loanId, borrower).orElseThrow(LoanNotFoundException::new);

        Money actualPaidAmount = amountToRepay.getAmount() > loan.getAmountOwed().getAmount() ?
                loan.getAmountOwed() : amountToRepay;

        loan.repay(actualPaidAmount);
    }

    @Transactional
    public void acceptLoan(final long loanApplicationId, final String lenderUsername) {
        User lender = findUser(lenderUsername);
        LoanApplication loanApplication = findLoanApplication(loanApplicationId);
//        User borrower = loanApplication.getBorrower();
//        Money money = loanApplication.getAmount();
//        lender.withDraw(money);
//        borrower.topUp(money);
        loanRepository.save(loanApplication.acceptLoanApplication(lender));
    }

    public List<Loan> findAllByBorrowedLoans(final User borrower, final Status status){
        return loanRepository.findAllByBorrowerAndStatus(borrower, status);
    }

    public List<Loan> findAllLentLoans(final User lender, final Status status){
        return loanRepository.findAllByLenderAndStatus(lender, status);
    }

    private LoanApplication findLoanApplication(long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
    }

    private User findUser(String lenderUsername) {
        return userRepository.findById(lenderUsername).orElseThrow(() -> new UserNotFoundException(lenderUsername));
    }

    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }
}
