package com.springboot.mortgagecalculator.service;

import com.springboot.mortgagecalculator.enums.LoanType;
import com.springboot.mortgagecalculator.exceptions.MortgageCalculatorException;
import com.springboot.mortgagecalculator.models.Dto.MortgageResultDto;
import com.springboot.mortgagecalculator.models.LoanDetails;
import com.springboot.mortgagecalculator.repository.MortgageCalculatorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MortgageCalculatorServiceImpl implements MortgageCalculatorService {
    private final MortgageCalculatorRepository mortgageCalculatorRepository;
    public MortgageCalculatorServiceImpl (MortgageCalculatorRepository mortgageCalculatorRepository) {
        this.mortgageCalculatorRepository = mortgageCalculatorRepository;
    }
    
    @Override
    public MortgageResultDto calculateMortgage (LoanDetails loanDetails) {
        validateLoanDetails( loanDetails );
        mortgageCalculatorRepository.save(loanDetails);
        
        if(loanDetails.getLoanType().equals( LoanType.FIXED)) {
            return calculateFixedRateMortgage(loanDetails);
        }
        return null;
    }
    
    @Override
    public List<MortgageResultDto> compareMortgage (List<LoanDetails> loanDetails) {
        List<LoanDetails> details = new ArrayList<>();
        for (  LoanDetails loanDetail : loanDetails) {
            validateLoanDetails( loanDetail );
            mortgageCalculatorRepository.save(loanDetail);
            details.add(loanDetail);
        }
        
        return details.stream()
                .map( this::calculateFixedRateMortgage )
                .toList();
    }
    
    private MortgageResultDto calculateFixedRateMortgage(LoanDetails loanDetails) {
        final int MONTHS_IN_YEAR = 12;
        
        final int PERCENT = 100;
        
        double loanAmount = loanDetails.getLoanAmount();
        double annualInterestRate = loanDetails.getAnnualInterestRate();
        int loanTermYears = loanDetails.getLoanTermYears();
        
        double monthlyInterestRate = annualInterestRate / MONTHS_IN_YEAR / PERCENT;
        
        int numberOfPayments = loanTermYears * MONTHS_IN_YEAR;
        
        double monthlyPayment = loanAmount * (monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments)));
        
        
        double totalPayment = monthlyPayment * numberOfPayments;
        
        double principalPaid = totalPayment - (monthlyPayment * numberOfPayments);
        
        double interestPaid = totalPayment - loanAmount;
        
        MortgageResultDto result = new MortgageResultDto();
        
        result.setMonthlyPayment(monthlyPayment);
        result.setPrincipal(principalPaid);
        result.setInterest(interestPaid);
        
        return result;
    }
    
    private void validateLoanDetails(LoanDetails loanDetails) {
        if (  loanDetails.getLoanAmount() <= 0 || loanDetails.getAnnualInterestRate() <= 0 || loanDetails.getLoanTermYears() <= 0) {
            throw new MortgageCalculatorException("Loan details must be greater than 0");
        }
    }
}
