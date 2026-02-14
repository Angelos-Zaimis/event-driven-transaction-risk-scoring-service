package com.angelos.transaction_risk_engine.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angelos.transaction_risk_engine.entity.RiskAssessmentEntity;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessmentEntity, UUID> {
    Optional<RiskAssessmentEntity> findByTransactionId(UUID transactionId);
}