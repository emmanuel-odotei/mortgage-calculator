package com.springboot.mortgagecalculator.controller;

import com.springboot.mortgagecalculator.exceptions.MortgageCalculatorException;
import com.springboot.mortgagecalculator.models.Dto.MortgageResultDto;
import com.springboot.mortgagecalculator.models.LoanDetails;
import com.springboot.mortgagecalculator.service.MortgageCalculatorServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/mortgage" )
public class MortgageController {
    
    private final MortgageCalculatorServiceImpl mortgageCalculatorService;
    
    public MortgageController (MortgageCalculatorServiceImpl mortgageCalculatorService) {
        this.mortgageCalculatorService = mortgageCalculatorService;
    }
    
    @PostMapping( "/calculate" )
    public MortgageResultDto calculateMortgage (@RequestBody LoanDetails loanDetails) {
        try {
            return mortgageCalculatorService.calculateMortgage( loanDetails);
        } catch ( Exception e ) {
            throw new MortgageCalculatorException( e.getMessage() );
        }
    }
    
    @PostMapping("/compare")
    public List<MortgageResultDto> compareMortgage(@RequestBody List<LoanDetails> loanDetails) {
        try {
            return mortgageCalculatorService.compareMortgage(loanDetails);
        } catch (Exception e) {
            throw new MortgageCalculatorException(e.getMessage());
        }
    }
}
