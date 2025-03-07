package com.rv.loan.repository;

import com.rv.loan.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans,Long> {

    Optional<Loans> findByMobileNumber(String mobileNumber);
    Optional<Loans>findByLoanNumber(String LoanNumber);
}
