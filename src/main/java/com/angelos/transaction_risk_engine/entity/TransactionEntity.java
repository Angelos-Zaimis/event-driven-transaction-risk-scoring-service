package com.angelos.transaction_risk_engine.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.angelos.transaction_risk_engine.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "transactions")
@Getter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "external_transaction_id", nullable = false, unique = true, length = 64)
    private String externalTransactionId;

    @Column(name = "customer_id", nullable = false, length = 64)
    private String customerId;

    @Column(name = "customer_age", nullable = false)
    private int customerAge;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, length = 2)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TransactionStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    private TransactionEntity(
            String externalTransactionId,
            String customerId,
            int customerAge,
            BigDecimal amount,
            String currency,
            String country
    ) {
        this.externalTransactionId = externalTransactionId;
        this.customerId = customerId;
        this.customerAge = customerAge;
        this.amount = amount;
        this.currency = currency;
        this.country = country;
        this.status = TransactionStatus.RECEIVED;
        this.createdAt = Instant.now();
    }

    public static TransactionEntity create(
            String externalTransactionId,
            String customerId,
            int customerAge,
            BigDecimal amount,
            String currency,
            String country
    ) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (customerAge < 0 || customerAge > 120) {
            throw new IllegalArgumentException("Invalid customer age");
        }

        return new TransactionEntity(
                externalTransactionId,
                customerId,
                customerAge,
                amount,
                currency,
                country
        );
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
