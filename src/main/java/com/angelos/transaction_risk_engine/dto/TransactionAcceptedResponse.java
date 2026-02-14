package com.angelos.transaction_risk_engine.dto;

import java.util.UUID;

public record TransactionAcceptedResponse(UUID transactionId, String status) {}