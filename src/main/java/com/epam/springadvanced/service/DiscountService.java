package com.epam.springadvanced.service;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.User;

import java.time.LocalDateTime;

public interface DiscountService {
    float getDiscount(User user, Event event, LocalDateTime dateTime);
}
