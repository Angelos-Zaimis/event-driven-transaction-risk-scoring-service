package com.angelos.transaction_risk_engine.entity;

import java.time.Instant;
import java.util.UUID;

import com.angelos.transaction_risk_engine.enums.RiskLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "risk_assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentEntity {

    @Id
    private UUID id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private UUID transactionId;

    @Column(nullable = false)
    private int score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private RiskLevel level;

    @Column(nullable = false, columnDefinition = "text")
    private String reasons;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    public static RiskAssessmentEntity create(UUID transactionId, int score, RiskLevel level, String reasons) {
        return new RiskAssessmentEntity(
                UUID.randomUUID(),
                transactionId,
                score,
                level,
                reasons,
                Instant.now()
        );
    }
}