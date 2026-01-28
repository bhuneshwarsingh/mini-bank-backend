package com.bhuneshwar.mini_bank.repository;

import com.bhuneshwar.mini_bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    // ✅ account statement = debit OR credit
    List<Transaction>findByDebitAccountOrCreditAccountOrderByTimestampDesc(String debit,String credit);

}
