package com.angelos.transaction_risk_engine.messaging.model;

import java.UUID;

public record TransactionReceivedEvent(UUID transactionId) {}