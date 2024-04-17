package com.springboot.mortgagecalculator.service;

import com.springboot.mortgagecalculator.models.Dto.MortgageResultDto;
import com.springboot.mortgagecalculator.models.LoanDetails;

import java.util.List;

public interface MortgageCalculatorService {

    MortgageResultDto calculateMortgage(LoanDetails loanDetails);
    
    List<MortgageResultDto> compareMortgage (List<LoanDetails> loanDetails);
}
