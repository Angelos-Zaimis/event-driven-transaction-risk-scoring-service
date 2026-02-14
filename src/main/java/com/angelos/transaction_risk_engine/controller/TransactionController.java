package com.angelos.transaction_risk_engine.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.angelos.transaction_risk_engine.dto.TransactionAcceptedResponse;
import com.angelos.transaction_risk_engine.dto.TransactionCreateRequest;
import com.angelos.transaction_risk_engine.service.TransactionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionAcceptedResponse> create(@Valid @RequestBody TransactionCreateRequest req) {
        UUID id = service.createOrGetTransaction(req);
        return ResponseEntity.accepted().body(new TransactionAcceptedResponse(id, "RECEIVED"));
    }
}
