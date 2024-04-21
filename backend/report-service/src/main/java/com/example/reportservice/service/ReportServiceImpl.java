package com.example.reportservice.service;

import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.dto.ReportResponseDto;
import com.example.reportservice.dto.TransactionResponseDto;
import com.example.reportservice.service.client.TransactionFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionFeignClient transactionFeignClient;

    @Override
    public ReportResponseDto getReport(Long fromAccountId) {
        ResponseEntity<List<TransactionResponseDto>> transactions = transactionFeignClient
                .getTransactionsByFromAccount(fromAccountId);
        if (transactions != null && transactions.getBody() != null) {
            return ReportResponseDto.builder()
                    .name("Report")
                    .description("Report description")
                    .content(transactions.getBody())
                    .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        }
        return null;
    }
}
