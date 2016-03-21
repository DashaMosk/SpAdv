package com.epam.springadvanced.service;

public enum Rating {
    HIGH(0), MID(1), LOW(2);

    private int value;
    private Rating(int value) {
        this.value = value;
    }

    public static Rating getRating(int rating) {
        if (rating >= 0 && rating < values().length) {
            return Rating.values()[rating];
        }
        return null;
    }

    public int getValue(){
        return value;
    }
}
