package com.example.userservice.domain.repositories;

import com.example.userservice.domain.model.Finance;

public interface FinanceRepository {

    public Finance addFinanceDetails(Finance fin);

    public Finance getFinanceDetails(Finance fin);

    public Finance updateFinanceDetails(Finance fin);

    public boolean deleteFinanceDetails(int userId);
}
