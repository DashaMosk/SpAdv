package com.epam.springadvanced.service.impl;

import com.epam.springadvanced.entity.UserAccount;
import com.epam.springadvanced.repository.AccountRepository;
import com.epam.springadvanced.repository.UserRepository;
import com.epam.springadvanced.service.AccountService;
import com.epam.springadvanced.service.exception.InsufficientAmountOfMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void refill(long userID, float amount) {
        UserAccount account = accountRepository.getByUserId(userID);
        if (account == null) {
            account = new UserAccount();
            account.setUser(userRepository.findById(userID));
        }
        account.setAmount(account.getAmount()+amount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void withdraw(long userID, float amount) throws InsufficientAmountOfMoneyException{
        UserAccount account = accountRepository.getByUserId(userID);
        if (account == null) {
            throw new InsufficientAmountOfMoneyException("Can't find account for user " + userID);
        }
        if(account.getAmount() - amount < 0) {
            throw new InsufficientAmountOfMoneyException("Insufficient amount of money, user "
                    + userID + ", rest " + account.getAmount() + ", amount " + amount);
        }
        account.setAmount(account.getAmount()-amount);
        accountRepository.save(account);
    }

    @Override
    public boolean hasMoney(long userID, float amount) {
        UserAccount account = accountRepository.getByUserId(userID);
        if ((account == null) || (account.getAmount() < amount)) {
            return false;
        }
        return true;
    }
}
