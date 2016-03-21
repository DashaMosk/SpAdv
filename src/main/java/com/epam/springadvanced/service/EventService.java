package com.epam.springadvanced.service;

import com.epam.springadvanced.entity.Auditorium;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.service.exception.AuditoriumAlreadyAssignedException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {
    Event create(String name, float price, Rating rating);

    Event create(String name, LocalDateTime dateTime, float price, Rating rating);

    void remove(Event event);
    Event getByName(String name);

    Event getById(long id);

    Collection<Event> getAll();
    Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
    Collection<Event> getNextEvents(LocalDateTime to);
    Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime) throws
            AuditoriumAlreadyAssignedException;
}
