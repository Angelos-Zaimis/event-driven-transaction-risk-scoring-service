package com.angelos.transaction_risk_engine.messaging.model;

import java.util.UUID;

public record TransactionReceivedEvent(UUID transactionId) {}