package com.example.userservice.domain.repositories;

import com.example.userservice.data.dao.FinanceDao;
import com.example.userservice.data.dao.UserDao;
import com.example.userservice.data.entities.FinanceEntity;
import com.example.userservice.data.entities.UserEntity;
import com.example.userservice.domain.model.Finance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinanceRepositoryImpl implements FinanceRepository{
    private final FinanceDao finDao;
    private final UserDao userDao;

    public Finance addFinanceDetails(Finance fin) {
        FinanceEntity finEnt = finDao.save(mapToEntity(fin));
        return mapToFinance(finEnt);
    }

    private FinanceEntity mapToEntity(Finance fin) {
        FinanceEntity finEnt = new FinanceEntity();
        finEnt.setUserId(mapToUserEntity(fin));
        finEnt.setBudget(fin.getBudget());
        finEnt.setSavings(fin.getSavings());
        return finEnt;
    }
    private UserEntity mapToUserEntity(Finance fin) {
        return userDao.findById(fin.getUserId()).orElse(null);
    }

    private Finance mapToFinance(FinanceEntity finEnt) {
        Finance fin = new Finance();
        fin.setId(finEnt.getId());
        fin.setUserId(finEnt.getUserId().getId());
        fin.setBudget(finEnt.getBudget());
        fin.setSavings(finEnt.getSavings());
        return fin;
    }

}
