package com.epam.springadvanced.web;

import com.epam.springadvanced.service.DiscountService;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/bookingservice")
public class DiscountController {
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private DiscountService discountService;

    @RequestMapping(path = "/discount", method = RequestMethod.GET)
    @ResponseBody
    public float getDiscount (@RequestParam final long userId, @RequestParam final long eventId,
                              @RequestParam final LocalDateTime dateTime) {
        return discountService.getDiscount(userService.getById(userId), eventService.getById(eventId), dateTime);
    }
}
