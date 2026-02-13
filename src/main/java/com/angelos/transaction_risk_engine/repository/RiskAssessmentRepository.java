package com.angelos.transaction_risk_engine.repository;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessmentEntity, UUID> {
    Optional<RiskAssessmentEntity> findByTransactionId(UUID transactionId);
}