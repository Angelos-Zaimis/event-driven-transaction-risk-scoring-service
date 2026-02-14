package com.angelos.transaction_risk_engine.risk;

import java.util.List;

import com.angelos.transaction_risk_engine.enums.RiskLevel;

public record RiskResult(int score, RiskLevel level, List<String> reasons) {}