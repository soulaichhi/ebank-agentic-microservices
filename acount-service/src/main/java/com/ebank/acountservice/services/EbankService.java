package com.ebank.acountservice.services;

import com.ebank.acountservice.domain.*;
import com.ebank.acountservice.exceptions.AccountNotFoundException;
import com.ebank.acountservice.exceptions.BalanceNotSufficientException;
import com.ebank.acountservice.exceptions.NegativeAmountException;
import com.ebank.acountservice.repositories.AccountRepository;
import com.ebank.acountservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public EbankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccountById(String accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Transactional
    public Account saveNewAccount(
            double initialBalance,
            AccountType type,
            String currency,
            Long customerId
    ){
        if (initialBalance < 0) {
            throw new NegativeAmountException("The amount should be positive");
        }
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .currency(currency)
                .type(type)
                .customerId(customerId)
                .status(AccountStatus.CREATED)
                .balance(initialBalance)
                .build();

        Account savedAccount= accountRepository.save(account);
        AccountTransaction transaction = AccountTransaction.builder()
                .amount(initialBalance)
                .type(TransactionType.CREDIT)
                .dateTime(LocalDateTime.now())
                .account(savedAccount)
                .build();

        transactionRepository.save(transaction);
        return savedAccount;
    }

    @Transactional
    public AccountTransaction credit(String accountId, double amount){
        if (amount <= 0) {
            throw new NegativeAmountException("The amount should be positive");
        }
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance()+amount);
        Account savedAccount = accountRepository.save(account);
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .type(TransactionType.CREDIT)
                .account(savedAccount)
                .build();
        return  transactionRepository.save(accountTransaction);

    }

    @Transactional
    public AccountTransaction debit(String accountId, double amount){
        if (amount <= 0) {
            throw new NegativeAmountException("The amount should be positive");
        }
        Account account = getAccountById(accountId);
        if (account.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance Not SufficientException");
        }
        account.setBalance(account.getBalance()-amount);
        Account savedAccount = accountRepository.save(account);
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .type(TransactionType.DEBIT)
                .account(savedAccount)
                .build();
        return  transactionRepository.save(accountTransaction);

    }



}
