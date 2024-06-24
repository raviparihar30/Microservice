package com.rv.loan.controller;

import com.rv.loan.constants.LoansConstants;
import com.rv.loan.dto.LoansDto;
import com.rv.loan.dto.ResponseDto;
import com.rv.loan.service.ILoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    private ILoanService iLoanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber){
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@Valid @RequestParam
                                                     @Pattern(regexp = "(^$[0-9]{10})",message = "Mobile number must be 10 digits")
                                                     String mobileNumber){
        LoansDto loansDto = iLoanService.fetchService(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody
                                                  LoansDto loansDto)
    {
        boolean isUpdated=iLoanService.updateLoan(loansDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));

        }
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@Valid @RequestParam
                                                      @Pattern(regexp = "(^$[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber){
        boolean isDeleted= iLoanService.deleteLoan(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE));

        }
    }


}
