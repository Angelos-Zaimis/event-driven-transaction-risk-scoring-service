package com.angelos.transaction_risk_engine.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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