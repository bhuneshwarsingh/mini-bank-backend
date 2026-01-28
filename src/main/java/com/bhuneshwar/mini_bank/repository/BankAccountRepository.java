package com.bhuneshwar.mini_bank.repository;

import com.bhuneshwar.mini_bank.entity.BankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findByCustomerUserEmail(String email);
}
