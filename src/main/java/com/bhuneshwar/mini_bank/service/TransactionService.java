package com.bhuneshwar.mini_bank.service;

import com.bhuneshwar.mini_bank.dto.*;
import com.bhuneshwar.mini_bank.entity.BankAccount;
import com.bhuneshwar.mini_bank.entity.Transaction;
import com.bhuneshwar.mini_bank.exception.BadRequestException;
import com.bhuneshwar.mini_bank.exception.InsufficientBalanceException;
import com.bhuneshwar.mini_bank.exception.ResourceNotFoundException;
import com.bhuneshwar.mini_bank.exception.UnauthorizedOperationException;
import com.bhuneshwar.mini_bank.repository.BankAccountRepository;
import com.bhuneshwar.mini_bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final BankAccountRepository accountRepo;
    private final TransactionRepository txnRepo;

    public TransactionService(BankAccountRepository accountRepo,TransactionRepository txnRepo)
    {
        this.accountRepo=accountRepo;
        this.txnRepo=txnRepo;
    }
    private String newTxnId()
    {
        return "TXNID" +UUID.randomUUID();
    }
    private void ensureAccountBelongsToUser(BankAccount acc,String loggedInEmail)
    {
        String ownerEmail=acc.getCustomer().getUser().getEmail();
        if(!ownerEmail.equalsIgnoreCase(loggedInEmail))
        {
            throw new UnauthorizedOperationException("Unathorized: this account doest belong to you");
        }
    }
    private void ensureActive(BankAccount acc) {
        if (!"ACTIVE".equalsIgnoreCase(acc.getStatus())) {
            throw new BadRequestException("Account is not active");
        }
    }

    private TransactionResponseDTO toDTO(Transaction t) {
        return new TransactionResponseDTO(
                t.getTxnId(),
                t.getType(),
                t.getAmount(),
                t.getDebitAccount(),
                t.getCreditAccount(),
                t.getDescription(),
                t.getBalanceAfter(),
                t.getTimestamp()
        );
    }
    public TransactionResponseDTO deposit(String email, DepositRequestDTO dto) {

        BankAccount acc = accountRepo.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        ensureAccountBelongsToUser(acc, email);
        ensureActive(acc);

        acc.setBalance(acc.getBalance() + dto.getAmount());
        accountRepo.save(acc);

        Transaction saved = txnRepo.save(new Transaction(
                newTxnId(),
                "DEPOSIT",
                dto.getAmount(),
                null, // ✅ debitAccount null (cash/upi/branch source)
                acc.getAccountNumber(), // ✅ creditAccount
                "Deposit to account",
                acc.getBalance()
        ));

        return toDTO(saved);
    }

    // ✅ WITHDRAW => debitAccount = userAccount, creditAccount = null
    public TransactionResponseDTO withdraw(String email, WithdrawRequestDTO dto) {

        BankAccount acc = accountRepo.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        ensureAccountBelongsToUser(acc, email);
        ensureActive(acc);

        if (acc.getBalance() < dto.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - dto.getAmount());
        accountRepo.save(acc);

        Transaction saved = txnRepo.save(new Transaction(
                newTxnId(),
                "WITHDRAW",
                dto.getAmount(),
                acc.getAccountNumber(), // ✅ debitAccount
                null, // ✅ creditAccount null
                "Withdraw from account",
                acc.getBalance()
        ));

        return toDTO(saved);
    }

    // ✅ TRANSFER => debitAccount = sender, creditAccount = receiver
    @Transactional
    public TransactionResponseDTO transfer(String email, TransferRequestDTO dto) {

        if (dto.getSenderAccount().equals(dto.getReceiverAccount())) {
            throw new BadRequestException("Sender and Receiver account cannot be same");
        }

        BankAccount sender = accountRepo.findByAccountNumber(dto.getSenderAccount())
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));

        BankAccount receiver = accountRepo.findByAccountNumber(dto.getReceiverAccount())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

        // ✅ sender account must belong to logged-in user
        ensureAccountBelongsToUser(sender, email);

        ensureActive(sender);
        ensureActive(receiver);

        if (sender.getBalance() < dto.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance in sender account");
        }

        // debit + credit
        sender.setBalance(sender.getBalance() - dto.getAmount());
        receiver.setBalance(receiver.getBalance() + dto.getAmount());

        accountRepo.save(sender);
        accountRepo.save(receiver);

        Transaction saved = txnRepo.save(new Transaction(
                newTxnId(),
                "TRANSFER",
                dto.getAmount(),
                sender.getAccountNumber(),   // ✅ debitAccount
                receiver.getAccountNumber(), // ✅ creditAccount
                "Transfer between accounts",
                sender.getBalance() // balanceAfter = sender balance
        ));

        return toDTO(saved);
    }

    // ✅ STATEMENT => debitAccount OR creditAccount match
    public List<TransactionResponseDTO> statement(String email, String accountNumber) {

        BankAccount acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        ensureAccountBelongsToUser(acc, email);

        return txnRepo.findByDebitAccountOrCreditAccountOrderByTimestampDesc(accountNumber, accountNumber)
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
