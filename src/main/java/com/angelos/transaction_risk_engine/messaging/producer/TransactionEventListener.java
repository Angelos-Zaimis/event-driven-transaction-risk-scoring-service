package com.angelos.transaction_risk_engine.messaging.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.angelos.transaction_risk_engine.messaging.model.TransactionReceivedEvent;

@Component
public class TransactionEventListener {

    private static final Logger log = LoggerFactory.getLogger(TransactionEventListener.class);

    private final TransactionEventProducer producer;

    public TransactionEventListener(TransactionEventProducer producer) {
        this.producer = producer;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionReceivedEvent(TransactionReceivedEvent event) {
        log.info("📤 Publishing Kafka message after commit for transaction: {}", event.transactionId());
        producer.publishReceivedEvent(event);
        log.info("✅ Kafka message published successfully");
    }
}
