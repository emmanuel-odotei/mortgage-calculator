package com.springboot.mortgagecalculator.repository;

import com.springboot.mortgagecalculator.models.LoanDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortgageCalculatorRepository  extends JpaRepository<LoanDetails, Long> {
}
