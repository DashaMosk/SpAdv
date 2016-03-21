package com.epam.springadvanced.service.impl;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.DiscountService;
import com.epam.springadvanced.service.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> strategies;


    @Override
    public float getDiscount(User user, Event event, LocalDateTime dateTime) {
        final double[] discount = {0};
        strategies.stream()
                .mapToDouble(strategy -> strategy.calculate(user))
                .max().ifPresent(d -> discount[0] = d);
        return (float) discount[0];
    }
}
