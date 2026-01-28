package com.bhuneshwar.mini_bank.controller;

import com.bhuneshwar.mini_bank.dto.AccountResponseDTO;
import com.bhuneshwar.mini_bank.dto.AccountCreateRequestDTO;
import com.bhuneshwar.mini_bank.service.BankAccountService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

   private final BankAccountService service;
   public BankAccountController(BankAccountService service)
   {
       this.service=service;
   }

   @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountCreateRequestDTO dto, Authentication auth)
   {
       String email= auth.getName();
       return ResponseEntity.ok(service.createAccount(email,dto));
   }
    @GetMapping("/me")
    public ResponseEntity<List<AccountResponseDTO>> myAccounts(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(service.getMyAccounts(email));
    }
}
