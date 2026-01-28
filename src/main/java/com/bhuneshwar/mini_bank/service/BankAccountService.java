package com.bhuneshwar.mini_bank.service;

import com.bhuneshwar.mini_bank.dto.AccountCreateRequestDTO;
import com.bhuneshwar.mini_bank.dto.AccountResponseDTO;
import com.bhuneshwar.mini_bank.entity.AppUser;
import com.bhuneshwar.mini_bank.entity.BankAccount;
import com.bhuneshwar.mini_bank.entity.Customer;
import com.bhuneshwar.mini_bank.repository.BankAccountRepository;
import com.bhuneshwar.mini_bank.repository.CustomerRepository;
import com.bhuneshwar.mini_bank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Service
public class BankAccountService {
    private final BankAccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final UserRepository userRepo;

    public BankAccountService(BankAccountRepository accountRepo,CustomerRepository customerRepo,UserRepository userRepo)
    {
        this.accountRepo=accountRepo;
        this.customerRepo=customerRepo;
        this.userRepo=userRepo;
    }
    private String generateAccountNumber()
    {
        return "MB"+new Random().nextInt(900000000);
    }
    private AccountResponseDTO toResponse(BankAccount acc)
    {
        return new AccountResponseDTO(acc.getAccountNumber(),acc.getAccountType(),acc.getBalance(),acc.getStatus());
    }
    public AccountResponseDTO createAccount(String email,AccountCreateRequestDTO dto)
    {
        AppUser user=userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Customer customer=customerRepo.findByUserEmail(email).orElse(null);
        if(customer==null)
        {
            customer=new Customer("NA","NA",user);
            customerRepo.save(customer);
        }
        String accNO=generateAccountNumber();

        BankAccount account = new BankAccount(
                accNO,
                dto.getAccountType().toUpperCase(),
                dto.getInitialBalance(),
                "ACTIVE",
                customer
        );
        BankAccount saved=accountRepo.save(account);
        return toResponse(saved);
    }
    public List<AccountResponseDTO> getMyAccounts(String email) {
        return accountRepo.findByCustomerUserEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
