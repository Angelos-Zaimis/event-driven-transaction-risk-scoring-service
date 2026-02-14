package com.angelos.transaction_risk_engine.dto;

import java.time.Instant;
import java.util.UUID;

public record RiskAssessmentResponse(
    UUID transactionId,
    int score,
    String level,
    String reasons,
    Instant assessedAt
) {}