package com.example.userservice.domain.repositories;

import com.example.userservice.domain.model.Finance;

public interface FinanceRepository {

    Finance addFinanceDetails(Finance fin);

    Finance getFinanceDetails(Finance fin);

    Finance updateFinanceDetails(int id, Finance finance);

    boolean deleteFinanceDetails(int userId);
}
