package com.angelos.transaction_risk_engine.repository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    Optional<TransactionEntity> findByExternalTransactionId(String externalTransactionId);
}