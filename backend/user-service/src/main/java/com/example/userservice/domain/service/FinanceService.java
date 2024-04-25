package com.example.userservice.domain.service;

import com.example.userservice.domain.model.Finance;
import com.example.userservice.domain.repositories.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FinanceService {
    private final FinanceRepository finRepo;

    public Finance addFinanceDetails(Finance fin) { return finRepo.addFinanceDetails(fin); }

    public Finance getFinanceDetails(Finance fin) { return finRepo.getFinanceDetails(fin); }

    public Finance updateFinanceDetails(Finance fin) { return finRepo.updateFinanceDetails(fin); }

    public boolean deleteFinanceDetails(int userId) { return finRepo.deleteFinanceDetails(userId); }

}
