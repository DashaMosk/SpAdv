package com.epam.springadvanced.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Rating> namesMap = new HashMap<String, Rating>(3);

    static {
        namesMap.put("HIGH", HIGH);
        namesMap.put("MID", MID);
        namesMap.put("LOW", LOW);
    }

    @JsonCreator
    public static Rating forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, Rating> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
