package com.ebank.acountservice.repositories;

import com.ebank.acountservice.domain.Account;
import com.ebank.acountservice.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
    List<AccountTransaction> findByAccountId(String accountId);
}
