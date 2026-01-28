package com.bhuneshwar.mini_bank.controller;

import com.bhuneshwar.mini_bank.dto.*;
import com.bhuneshwar.mini_bank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tx")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> deposit(
            @Valid @RequestBody DepositRequestDTO dto,
            Authentication auth) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Deposit successful",
                        service.deposit(auth.getName(), dto)
                )
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> withdraw(
            @Valid @RequestBody WithdrawRequestDTO dto,
            Authentication auth) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Withdraw successful",
                        service.withdraw(auth.getName(), dto)
                )
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> transfer(
            @Valid @RequestBody TransferRequestDTO dto,
            Authentication auth) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Transfer successful",
                        service.transfer(auth.getName(), dto)
                )
        );
    }

    @GetMapping("/statement/{accountNumber}")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> statement(
            @PathVariable String accountNumber,
            Authentication auth) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Statement fetched",
                        service.statement(auth.getName(), accountNumber)
                )
        );
    }
}
