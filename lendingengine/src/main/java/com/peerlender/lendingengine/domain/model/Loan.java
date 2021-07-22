package com.peerlender.lendingengine.domain.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    @OneToOne(cascade = CascadeType.ALL)
    private Money loanAmount;
    private double interestRate;
    private LocalDate dateLent;
    private LocalDate dateDue;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amountRepayed;
    private Status status;

    public Loan() {
    }

    public Loan(User lender, LoanApplication loanApplication) {
        this.borrower = loanApplication.getBorrower();
        this.lender = lender;
        this.loanAmount = loanApplication.getAmount();
        this.interestRate = loanApplication.getInterestRate();
        this.dateLent = LocalDate.now();
        this.dateDue = LocalDate.now().plusDays(loanApplication.getRepaymentTermInDays());
        this.amountRepayed = Money.ZERO;
        this.status = Status.ONGOING;
    }

    public void repay(final Money money) {
        borrower.withDraw(money);
        lender.topUp(money);
        amountRepayed = amountRepayed.add(money);

        if (getAmountOwed().equals(Money.ZERO)) {
            status = Status.COMPLETED;
        }
    }

    public Money getAmountOwed() {
        return loanAmount.times(1 + interestRate / 100d).minus(amountRepayed);
    }

    public long getId() {
        return id;
    }

    public User getBorrower() {
        return borrower;
    }

    public User getLender() {
        return lender;
    }

    public Money getAmountRepayed() {
        return amountRepayed;
    }
}
