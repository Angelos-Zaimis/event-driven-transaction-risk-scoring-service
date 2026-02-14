package com.angelos.transaction_risk_engine.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    
    public static final String TOPIC_RECEIVED = "transactions.received";

    @Bean
    NewTopic receivedTopic() {
        return TopicBuilder.name(TOPIC_RECEIVED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}