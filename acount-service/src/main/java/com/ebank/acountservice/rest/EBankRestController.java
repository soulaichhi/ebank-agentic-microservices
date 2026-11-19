package com.ebank.acountservice.rest;

import com.ebank.acountservice.domain.Account;
import com.ebank.acountservice.domain.AccountTransaction;
import com.ebank.acountservice.domain.AccountType;
import com.ebank.acountservice.services.EbankService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EBankRestController {

    private final EbankService eBankService;

    public EBankRestController(EbankService eBankService) {
        this.eBankService = eBankService;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return eBankService.getAccountById(accountId);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return eBankService.getAllAccounts();
    }

    @PostMapping("/accounts/save")
    public Account saveNewAccount(double initialBalance, AccountType type, String currency, Long customerId) {
        return eBankService.saveNewAccount(initialBalance, type, currency, customerId);
    }

    @PostMapping("/accounts/credit")
    public AccountTransaction credit(String accountId, double amount) {
        return eBankService.credit(accountId, amount);
    }

    @PostMapping("/accounts/debit")
    public AccountTransaction debit(String accountId, double amount) {
        return eBankService.debit(accountId, amount);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e){
        return  ResponseEntity.internalServerError().body(Map.of("errorMessage", e.getMessage()));
    }

}
