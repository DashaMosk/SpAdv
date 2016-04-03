package com.epam.springadvanced.service;

import com.epam.springadvanced.service.exception.InsufficientAmountOfMoneyException;

public interface AccountService {
    void refill(long userID, float amount);
    void withdraw(long userID, float amount) throws InsufficientAmountOfMoneyException;
    boolean hasMoney(long userID, float amount);
}
