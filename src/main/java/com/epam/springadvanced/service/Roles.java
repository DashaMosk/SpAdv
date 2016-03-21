package com.epam.springadvanced.service;

public enum Roles {
    REGISTERED_USER("Registered user"),
    BOOKING_MANAGER("Booking manager");

    private String desc;

    Roles(String desc) {
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}
