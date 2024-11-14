package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    /**
     * Create a new account based on the information in the given customer DTO.
     *
     * @param customerDto The customer DTO with the information to create the account.
     */
    /**
     * Create a new account for the given customer.
     * @param customer The customer for whom the account is to be created.
     * @return The newly created account.
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "+customerDto.getMobileNumber());//=custom
        }
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(savedCustomer));
    }

    private Accounts createNewAccounts(Customer customer){
        Accounts accounts=new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber=1000000000L+new Random().nextLong(9000000000L);
        accounts.setAccountNumber(randomAccNumber);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
//        accounts.setCreatedAt(LocalDateTime.now());
//        accounts.setCreatedBy("Anonymous");
        return accounts;
    }

    /**
     * Fetches account details for a customer using their mobile number.
     * @param mobileNumber The mobile number of the customer.
     * @return A CustomerDto object containing the customer's account details.
     * @throws ResourceNotFoundException if the customer or account is not found.
     */
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobile number",mobileNumber)

        );
        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );
        CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    /**
     * Updates an existing customer's account information based on the provided CustomerDto.
     *
     * This method retrieves the account and customer details using the account number and customer ID
     * from the provided CustomerDto. It maps the new information to the existing account and customer
     * entities, and saves the updated entities back to the database.
     *
     * @param customerDto The CustomerDto object containing the updated account and customer details.
     * @return true if the update was successful, false if the account or customer is not found.
     * @throws ResourceNotFoundException if the account or customer is not found in the database.
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * Deletes an existing account for a customer based on the provided mobile number.
     *
     * This method first retrieves the customer details using the provided mobile number.
     * It then deletes the associated account and customer details from the database.
     *
     * @param mobileNumber the mobile number of the customer.
     * @return true if the deletion was successful, false if the customer or account is not found.
     * @throws ResourceNotFoundException if the customer or account is not found in the database.
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
