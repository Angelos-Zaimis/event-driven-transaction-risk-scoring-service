package com.angelos.transaction_risk_engine.messaging.consumer;

import java.util.UUID;

import org.checkerframework.checker.units.qual.t;
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
        UUID id = event.transactionId();

        TransactionEntity tx = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Transaction not found: " + id));
        
        RiskResult result = riskScoringService.score(tx);

        RiskAssessmentEntity riskAssessmentEntity = RiskAssessmentEntity.create(id, result.score(), result.level(), String.join("; ", result.reasons()));
        riskAssessmentRepository.save(riskAssessmentEntity);

        tx.setStatus(TransactionStatus.SCORED);
        transactionRepository.save(tx);
    }

}
