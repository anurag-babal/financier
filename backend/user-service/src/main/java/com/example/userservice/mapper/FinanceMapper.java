package com.example.userservice.mapper;

import com.example.userservice.domain.model.Finance;
import com.example.userservice.dto.*;
import org.springframework.stereotype.Component;

@Component
public class FinanceMapper {
    public Finance mapCreateReqToFinance(FinanceCreateRequestDto financeCreateRequestDto) {
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

    public Finance mapGetReqToFinance(FinanceDetailsRequestDto financeDetailsRequestDto) {
        Finance fin = new Finance();
        fin.setUserId(financeDetailsRequestDto.getUserId());
        return fin;
    }

    public FinanceDetailsResponseDto mapToFinanceDetailsResponse(Finance fin) {
        FinanceDetailsResponseDto financeDetailsResponseDto = new FinanceDetailsResponseDto();
        financeDetailsResponseDto.setBudget(fin.getBudget());
        financeDetailsResponseDto.setSavings(fin.getSavings());
        return financeDetailsResponseDto;
    }

    public Finance mapUpdateReqToFinance(FinanceUpdateRequestDto financeUpdateRequestDto) {
        Finance fin = new Finance();
        fin.setUserId(financeUpdateRequestDto.getUserId());
        fin.setBudget(financeUpdateRequestDto.getBudget());
        fin.setSavings(financeUpdateRequestDto.getSavings());
        return fin;
    }
}
