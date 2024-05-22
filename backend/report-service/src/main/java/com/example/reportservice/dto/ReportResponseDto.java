package com.example.reportservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportResponseDto {
    private String name;
    private String description;
    private List<ExpenseDto> content;
    private String createdAt;
}
