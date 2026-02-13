package com.angelos.transaction_risk_engine.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record TransactionCreateRequest(

        @NotBlank
        @Size(max = 64, message = "External transaction ID must be at most 64 characters")
        String externalTransactionId,

        @NotBlank
        @Size(max = 64, message = "Customer ID must be at most 64 characters")
        String customerId,
        
        @Min(value = 0, message = "Customer age must be at least 0")
        @Max(value = 120, message = "Customer age must be at most 120")
        int customerAge,
        
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
        BigDecimal amount,
       
        @NotBlank
        @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
        String currency,

        @NotBlank
        @Size(min = 2, max = 2, message = "Country must be exactly 2 characters")
        String country

) {}
