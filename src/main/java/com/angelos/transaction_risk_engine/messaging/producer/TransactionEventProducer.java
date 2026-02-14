package com.angelos.transaction_risk_engine.messaging.producer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.angelos.transaction_risk_engine.config.KafkaConfig;
import com.angelos.transaction_risk_engine.messaging.model.TransactionReceivedEvent;

@Component
public class TransactionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReceivedEvent(TransactionReceivedEvent event) {
        kafkaTemplate.send(KafkaConfig.TOPIC_RECEIVED, event.transactionId().toString(), event);
    }
}
