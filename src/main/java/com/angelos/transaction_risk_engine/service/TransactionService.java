package com.angelos.transaction_risk_engine.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angelos.transaction_risk_engine.dto.TransactionCreateRequest;
import com.angelos.transaction_risk_engine.entity.TransactionEntity;
import com.angelos.transaction_risk_engine.messaging.model.TransactionReceivedEvent;
import com.angelos.transaction_risk_engine.repository.TransactionRepository;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionService(TransactionRepository transactionRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID createOrGetTransaction(TransactionCreateRequest request) {
        return transactionRepository
                .findByExternalTransactionId(request.externalTransactionId())
                .map(TransactionEntity::getId)
                .orElseGet(() -> createNewTransaction(request));
    }

    private UUID createNewTransaction(TransactionCreateRequest request) {
        TransactionEntity entity = TransactionEntity.create(
                request.externalTransactionId(),
                request.customerId(),
                request.customerAge(),
                request.amount(),
                request.currency(),
                request.country()
        );

        TransactionEntity savedEntity = transactionRepository.save(entity);

        // Publish event after transaction commits
        eventPublisher.publishEvent(new TransactionReceivedEvent(savedEntity.getId()));
    
        return savedEntity.getId();
    }
}