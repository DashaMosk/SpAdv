package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.UserAccount;
import com.epam.springadvanced.repository.TicketRepository;
import com.epam.springadvanced.service.*;
import com.epam.springadvanced.service.exception.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class BookingController {

    @Autowired
    BookingService bookingService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    AccountService accountService;
    @Autowired
    TicketRepository ticketRepository;

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
    public void bookTicket(@RequestParam final long userId, @RequestParam final long ticketId)
            throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException,
            InsufficientAmountOfMoneyException {
        bookingService.bookTicket(userService.getById(userId), ticketRepository.getById(ticketId));
    }

    @RequestMapping(path = "/booking/tickets", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getTickets(@RequestParam final long eventId, @RequestParam final LocalDateTime dateTime){
        bookingService.getTicketsForEvent(eventService.getById(eventId), dateTime);
        return "tickets";
    }

    @RequestMapping(path = "/booking/tickets", produces={"application/pdf"})
    public void getTicketsRepByEvent(HttpServletResponse response, @RequestParam final long eventId,
                                     @RequestParam final LocalDateTime dateTime) throws JRException, IOException {
        reportService.getTicketRepPDF(response, TRepKind.FOREVENT, 0, eventService.getById(eventId), dateTime);
    }

    @RequestMapping(path = "/booking/refill", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void refillAccount(@RequestParam final long userId, @RequestBody final UserAccount account) {
        accountService.refill(userId, account.getAmount());
    }

}
