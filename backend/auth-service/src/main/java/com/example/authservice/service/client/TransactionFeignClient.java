package com.example.authservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transaction-service")
public interface TransactionFeignClient {

    @GetMapping("/api/transactions")
    public ResponseEntity<String> getTransactions();
}
