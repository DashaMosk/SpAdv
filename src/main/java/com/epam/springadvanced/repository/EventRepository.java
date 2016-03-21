package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.Event;

import java.util.Collection;

public interface EventRepository {
    Event save(Event event);
    void delete(long id);
    Event getByName(String name);

    Event getById(Long id);

    Collection<Event> getAll();
}
