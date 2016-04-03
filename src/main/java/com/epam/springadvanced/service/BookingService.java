package com.epam.springadvanced.service;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.exception.*;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {
    float getTicketPrice(Event event, LocalDateTime dateTime, Collection<Integer> seatNumbers, User user) throws UserNotRegisteredException, EventNotAssignedException;

    void bookTicket(User user, Ticket ticket) throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException, InsufficientAmountOfMoneyException;

    Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime dateTime);
}
