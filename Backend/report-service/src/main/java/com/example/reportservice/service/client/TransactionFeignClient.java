package com.example.reportservice.service.client;

import com.example.reportservice.dto.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("transaction-service")
public interface TransactionFeignClient {
    @GetMapping(value = "/api/v1/transactions/accounts/{fromAccountId}", consumes = "application/json")
    ResponseEntity<List<TransactionResponseDto>> getTransactionsByFromAccount(@PathVariable Long fromAccountId);
}
