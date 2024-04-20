package com.example.reportservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Report {
    private String name;
    private String description;
//    private List<TransactionDto> content;
    private String createdAt;
}
