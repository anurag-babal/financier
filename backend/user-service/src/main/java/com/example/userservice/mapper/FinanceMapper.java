package com.example.userservice.mapper;

import com.example.userservice.domain.model.Finance;
import com.example.userservice.dto.FinanceCreateRequestDto;
import com.example.userservice.dto.FinanceCreateResponseDto;
import org.springframework.stereotype.Component;

@Component
public class FinanceMapper {
    public Finance mapToFinance(FinanceCreateRequestDto financeCreateRequestDto) {
        Finance fin = new Finance();
        fin.setUserId(financeCreateRequestDto.getUserId());
        fin.setBudget(financeCreateRequestDto.getBudget());
        fin.setSavings(financeCreateRequestDto.getSavings());
        return fin;
    }

    public FinanceCreateResponseDto mapToFinanceCreateResponseDto(Finance fin) {
        FinanceCreateResponseDto financeCreateResponseDto = new FinanceCreateResponseDto();
        financeCreateResponseDto.setId(fin.getId());
        financeCreateResponseDto.setUserId(fin.getUserId());
        financeCreateResponseDto.setBudget(fin.getBudget());
        financeCreateResponseDto.setSavings(fin.getSavings());
        return financeCreateResponseDto;
    }
}
