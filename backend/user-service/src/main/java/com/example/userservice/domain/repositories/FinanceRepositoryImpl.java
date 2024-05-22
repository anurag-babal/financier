package com.example.userservice.domain.repositories;

import com.example.userservice.data.dao.FinanceDao;
import com.example.userservice.data.dao.UserDao;
import com.example.userservice.data.entities.FinanceEntity;
import com.example.userservice.data.entities.UserEntity;
import com.example.userservice.domain.model.Finance;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FinanceRepositoryImpl implements FinanceRepository{
    private final FinanceDao finDao;
    private final UserDao userDao;

    public Finance addFinanceDetails(Finance fin) {
        FinanceEntity finEnt = finDao.save(mapToFinanceEntity(fin));
        return mapToFinance(finEnt);
    }

    private FinanceEntity mapToFinanceEntity(Finance finance) {
        FinanceEntity finEnt = new FinanceEntity();
        finEnt.setUserId(mapToUserEntity(finance));
        finEnt.setBudget(BigDecimal.valueOf(finance.getBudget()));
        finEnt.setSavings(BigDecimal.valueOf(finance.getSavings()));
        return finEnt;
    }
    private UserEntity mapToUserEntity(Finance fin) {
        return userDao.findById(fin.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private Finance mapToFinance(FinanceEntity finEnt) {
        Finance fin = new Finance();
        fin.setId(finEnt.getId());
        fin.setUserId(finEnt.getUserId().getId());
        fin.setBudget(finEnt.getBudget().doubleValue());
        fin.setSavings(finEnt.getSavings().doubleValue());
        return fin;
    }

    public Finance getFinanceDetails(Finance fin) {
        FinanceEntity finEnt = finDao.findByUserIdId(fin.getUserId()).orElse(null);
        return mapToFinance(finEnt);
    }

    public Finance updateFinanceDetails(int id, Finance finance) {
        FinanceEntity financeEntity = mapToFinanceEntity(finance);
        financeEntity.setId(id);
        financeEntity = finDao.save(financeEntity);
        return mapToFinance(financeEntity);
    }

    public boolean deleteFinanceDetails(int userId) {
        FinanceEntity finEnt = finDao.findByUserIdId(userId).orElse(null);
        finDao.delete(finEnt);
        return true;
    }
}
