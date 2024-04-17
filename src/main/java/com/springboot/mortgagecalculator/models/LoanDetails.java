package com.springboot.mortgagecalculator.models;

import com.springboot.mortgagecalculator.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double loanAmount;
    
    private Double annualInterestRate;
    
    private Integer loanTermYears;
    
    private LoanType loanType;
}
