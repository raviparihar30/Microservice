package com.rv.loan.service.impl;

import com.rv.loan.constants.LoansConstants;
import com.rv.loan.dto.LoansDto;
import com.rv.loan.entity.Loans;
import com.rv.loan.exception.LoanAlreadyExistsException;
import com.rv.loan.exception.ResourceNotFoundException;
import com.rv.loan.mapper.LoansMapper;
import com.rv.loan.repository.LoansRepository;
import com.rv.loan.service.ILoanService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ILoanServiceImpl implements ILoanService {
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoansDto fetchService(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loans","MobileNumber",mobileNumber)
                );

        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated=false;
        Loans loans=loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(()->new ResourceNotFoundException("Loan","LoanNumber",loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Loan","MobileNumnber",mobileNumber));
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan=new Loans();
        Long randomLoanNumber=100000000000L+new Random().nextInt(90000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }


}
