package com.rv.loan.service;

import com.rv.loan.dto.LoansDto;

public interface ILoanService {
    void createLoan(String mobileNumber);

    LoansDto fetchService(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
