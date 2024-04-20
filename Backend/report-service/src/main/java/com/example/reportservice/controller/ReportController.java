package com.example.reportservice.controller;

import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.dto.ReportResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{fromAccountId}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable Long fromAccountId) {
        ReportResponseDto report = reportService.getReport(fromAccountId);
        if (report != null) {
            return ResponseEntity.ok(report);
        }
        return ResponseEntity.notFound().build();
    }
}
