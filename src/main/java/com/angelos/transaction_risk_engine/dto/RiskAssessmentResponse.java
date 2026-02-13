package com.angelos.transaction_risk_engine.dto;

public record RiskAssessmentResponse(
    UUID transactionId,
    int score,
    String level,
    String reasons,
    Instant assessedAt
) {}