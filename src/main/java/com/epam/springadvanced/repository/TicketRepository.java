package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;

import java.util.Collection;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Ticket getById(Long id);
    Collection<Ticket> getAll();
    Collection<Ticket> getByEventName(String eventName);
    Collection<Ticket> getBookedTickets();
    Collection<Ticket> getBookedTicketsByUserId(long userId);
    void saveBookedTicket(User user, Ticket ticket);
    void deleteBookedTicketByUserId(long userId);
}
