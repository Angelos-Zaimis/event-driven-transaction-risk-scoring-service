package com.angelos.transaction_risk_engine.dto;

public record TransactionDetailsResponse(
    UUID id,
    String externalTransactionId,
    String customerId,
    int customerAge,
    BigDecimal amount,
    String currency,
    String country,
    String status,
    Instant createdAt
) {}