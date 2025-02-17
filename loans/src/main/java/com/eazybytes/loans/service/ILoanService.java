package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;

public interface ILoanService {

    /**
     * Creates a new loan for a customer using their mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @throws LoanAlreadyExistsException if a loan already exists for the provided mobile number.
     */
    void createLoan(String mobileNumber);

    /**
     * Fetches loan details for a customer using their mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return A LoansDto object containing the customer's loan details.
     * @throws ResourceNotFoundException if the loan is not found for the given mobile number.
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     * Updates an existing loan for a customer based on the information in the given loan DTO.
     *
     * @param loansDto The loan DTO with the information to update the loan.
     * @return true if the update was successful, false if the loan is not found.
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     * Deletes a loan for a customer using their mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return true if the loan was deleted successfully, false if the loan is not found.
     */
    boolean deleteLoan(String mobileNumber);
}
