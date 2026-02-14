package com.angelos.transaction_risk_engine.risk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.angelos.transaction_risk_engine.entity.TransactionEntity;
import com.angelos.transaction_risk_engine.enums.RiskLevel;

@Service
public class RiskScoringService {

    public RiskResult score(TransactionEntity tx) {
        int score = 0;
        List<String> reasons = new ArrayList<>();
        
        // Example scoring logic
         if (tx.getAmount().doubleValue() > 10_000) {
            score += 30;
            reasons.add("AMOUNT_GT_10000");
        }
        if (tx.getAmount().doubleValue() > 50_000) {
            score += 60;
            reasons.add("AMOUNT_GT_50000");
        }

        if (List.of("RU", "IR", "KP").contains(tx.getCountry().toUpperCase())) {
            score += 40;
            reasons.add("HIGH_RISK_COUNTRY");
        }

        if (tx.getCustomerAge() < 25 && tx.getAmount().doubleValue() > 5_000) {
            score += 20;
            reasons.add("YOUNG_CUSTOMER_LARGE_AMOUNT");
        }

        score = Math.min(score, 100);

        RiskLevel level = score >= 70 ? RiskLevel.HIGH : (score >= 40 ? RiskLevel.MEDIUM : RiskLevel.LOW);
        return new RiskResult(score, level, reasons);
    }
}
