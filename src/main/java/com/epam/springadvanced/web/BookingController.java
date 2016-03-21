package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.service.UserService;
import com.epam.springadvanced.service.exception.EventNotAssignedException;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@RequestMapping("/bookingservice")
public class BookingController {

    @Autowired
    BookingService bookingService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @RequestMapping(path = "/booking/cost", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public float getCost(@RequestParam final long eventId, @RequestParam final LocalDateTime dateTime,
                         @RequestParam final long userId, @RequestBody final Collection<Integer> seats)
                         throws UserNotRegisteredException, EventNotAssignedException {
        return bookingService.getTicketPrice(eventService.getById(eventId), dateTime, seats, userService.getById(userId));
    }

    @RequestMapping(path = "/booking/book", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void bookTicket(@RequestParam final long userId, @RequestBody final Ticket ticket)
            throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException{
        bookingService.bookTicket(userService.getById(userId), ticket);
    }

    @RequestMapping(path = "/booking/tickets", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getTickets(@RequestParam final long eventId, @RequestParam final LocalDateTime dateTime){
        bookingService.getTicketsForEvent(eventService.getById(eventId), dateTime);
        return "tickets";
    }

}
