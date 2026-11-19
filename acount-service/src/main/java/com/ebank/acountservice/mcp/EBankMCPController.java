package com.ebank.acountservice.mcp;

import com.ebank.acountservice.domain.Account;
import com.ebank.acountservice.domain.AccountTransaction;
import com.ebank.acountservice.domain.AccountType;
import com.ebank.acountservice.services.EbankService;
import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EBankMCPController {
    private EbankService eBankService;

    public EBankMCPController(EbankService eBankService) {
        this.eBankService = eBankService;
    }

    @McpTool(description = "Get Account By Id")
    public Account getAccountById(@McpArg(description = "The account id") String accountId) {
        return eBankService.getAccountById(accountId);
    }

    @McpTool(description = "Get All Accounts")
    public List<Account> getAllAccounts() {
        return eBankService.getAllAccounts();
    }

    @McpTool(description = "Save a new Account")
    public Account saveNewAccount(
            @McpArg(description = "The initial balance") double initialBalance,
            @McpArg(description = "The account type") AccountType type,
            @McpArg(description = "The currency of account") String currency,
            @McpArg(description = "The customer id") Long customerId) {
        return eBankService.saveNewAccount(initialBalance, type, currency, customerId);
    }

    @McpTool(description = "Credit an Account")
    public AccountTransaction credit(
            @McpArg(description = "The account Id") String accountId,
            @McpArg(description = "The amount to credit") double amount) {
        return eBankService.credit(accountId, amount);
    }

    @McpTool(description = "Debit an Account")
    public AccountTransaction debit(
            @McpArg(description = "The account Id") String accountId,
            @McpArg(description = "The amount to debit") double amount) {
        return eBankService.debit(accountId, amount);
    }


}
