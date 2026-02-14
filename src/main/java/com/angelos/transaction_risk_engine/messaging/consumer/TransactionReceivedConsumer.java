package com.angelos.transaction_risk_engine.messaging.consumer;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.angelos.transaction_risk_engine.entity.RiskAssessmentEntity;
import com.angelos.transaction_risk_engine.entity.TransactionEntity;
import com.angelos.transaction_risk_engine.enums.TransactionStatus;
import com.angelos.transaction_risk_engine.messaging.model.TransactionReceivedEvent;
import com.angelos.transaction_risk_engine.repository.RiskAssessmentRepository;
import com.angelos.transaction_risk_engine.repository.TransactionRepository;
import com.angelos.transaction_risk_engine.risk.RiskResult;
import com.angelos.transaction_risk_engine.risk.RiskScoringService;

@Component
public class TransactionReceivedConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionReceivedConsumer.class);

    private final TransactionRepository transactionRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskScoringService riskScoringService;

    public TransactionReceivedConsumer(TransactionRepository transactionRepository,
                                       RiskAssessmentRepository riskAssessmentRepository,
                                       RiskScoringService riskScoringService) {
        this.transactionRepository = transactionRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
        this.riskScoringService = riskScoringService;
    }

    @KafkaListener(topics = "transactions.received")
    @Transactional
    public void onMessage(TransactionReceivedEvent event) {
        log.info("🔔 Received Kafka message for transaction: {}", event.transactionId());
        UUID id = event.transactionId();

        log.info("Looking up transaction in database: {}", id);
        TransactionEntity tx = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Transaction not found: " + id));
        
        log.info("Found transaction: externalId={}, amount={}, country={}", 
                 tx.getExternalTransactionId(), tx.getAmount(), tx.getCountry());
        
        log.info("Calculating risk score...");
        RiskResult result = riskScoringService.score(tx);
        log.info("Risk score calculated: score={}, level={}, reasons={}", 
                 result.score(), result.level(), result.reasons());

        log.info("Creating risk assessment entity...");
        RiskAssessmentEntity riskAssessmentEntity = RiskAssessmentEntity.create(id, result.score(), result.level(), String.join("; ", result.reasons()));
        RiskAssessmentEntity saved = riskAssessmentRepository.save(riskAssessmentEntity);
        log.info("Risk assessment saved to database with ID: {}", saved.getId());

        log.info("Updating transaction status to SCORED...");
        tx.setStatus(TransactionStatus.SCORED);
        transactionRepository.save(tx);
        log.info("Transaction processing completed successfully for: {}", id);
    }

}
