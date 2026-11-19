package com.ebank.acountservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    private Account account;
}
