package com.epam.springadvanced.service.exception;

public class InsufficientAmountOfMoneyException extends Exception{
    public InsufficientAmountOfMoneyException(String msg) {
        super(msg);
    }
}
