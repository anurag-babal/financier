package com.example.reportservice.domain.service;

import com.example.reportservice.dto.ReportResponseDto;

public interface ReportService {
    ReportResponseDto getReport(Long fromAccountId);
}
