package com.example.reportservice.controller;

import com.example.reportservice.domain.model.Report;
import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.dto.CategoryDto;
import com.example.reportservice.dto.ExpenseDto;
import com.example.reportservice.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ResponseDto> getExpensesReport(
            @RequestParam String userId,
            @RequestParam(defaultValue = "all") String category,
            @RequestParam(defaultValue = "all") String year,
            @RequestParam(defaultValue = "all") String month
    ) {
        List<ExpenseDto> expenses = reportService.getExpensesReport(userId);
        List<CategoryDto> categories = reportService.getCategories();
        Report report = reportService.generateReport(expenses, categories, category, year, month);
        return new ResponseEntity<>(
                new ResponseDto(
                        HttpStatus.OK,
                        "Report generated successfully",
                        report
                ),
                HttpStatus.OK
        );
    }
}
