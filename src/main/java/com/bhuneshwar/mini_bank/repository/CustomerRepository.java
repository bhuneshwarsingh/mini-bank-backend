package com.bhuneshwar.mini_bank.repository;

import com.bhuneshwar.mini_bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
    Optional<Customer>findByUserEmail(String email);
}
