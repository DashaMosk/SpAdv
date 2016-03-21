package com.epam.springadvanced.entity;

import java.io.Serializable;

public class Seat implements Serializable{
    private int number;
    private boolean vip;

    public Seat(int number, boolean vip) {
        this.number = number;
        this.vip = vip;
    }

    public Seat(int number) {
        this.number = number;
        this.vip = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
