package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account based on the information in the given customer DTO.
     *
     * @param customerDto The customer DTO with the information to create the account.
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetches account details for a customer using their mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return A CustomerDto object containing the customer's account details.
     * @throws ResourceNotFoundException if the customer or account is not found.
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     * Updates an existing account for a customer based on the information in the given customer DTO.
     *
     * @param customerDto The customer DTO with the information to update the account.
     * @return true if the update was successful, false if the customer or account is not found.
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Deletes an existing account for a customer using their mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return true if the deletion was successful, false if the customer or account is not found.
     */
    boolean deleteAccount(String mobileNumber);

}
