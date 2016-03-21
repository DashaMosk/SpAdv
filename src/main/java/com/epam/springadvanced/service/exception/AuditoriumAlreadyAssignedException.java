package com.epam.springadvanced.service.exception;

import com.epam.springadvanced.entity.Auditorium;
import com.epam.springadvanced.entity.Event;

public class AuditoriumAlreadyAssignedException extends Exception {
    private String message;

    public AuditoriumAlreadyAssignedException(Event event, Auditorium auditorium) {
        message = String.format("Event <%s> is not assigned to auditorium <%s> because it's already assigned to another event",
                event.getName(),
                auditorium.getName());
    }

    @Override
    public String getMessage() {
        return message;
    }
}
