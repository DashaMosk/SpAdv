package com.epam.springadvanced.aspect;

import com.epam.springadvanced.repository.CounterRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CounterAspect {

    private static final Logger log = LoggerFactory.getLogger(CounterAspect.class);

    @Autowired
    private CounterRepository counterRepository;

    @Pointcut("execution(* com.epam.springadvanced.service.EventService.getByName(..))")
    public void pointcutEventByName() {
    }

    @Pointcut("execution(* com.epam.springadvanced.service.BookingService.getTicketPrice(..))")
    public void pointcutPriceQueried() {
    }

    @Pointcut("execution(* com.epam.springadvanced.service.BookingService.bookTicket(..))")
    public void pointcutTicketBooked() {
    }

    @AfterReturning("pointcutEventByName()")
    public void countEventAccessedByName() {
        Integer count = counterRepository.getByName(Counters.EVENT_ACCESSED_BY_NAME.name()) + 1;
        counterRepository.save(Counters.EVENT_ACCESSED_BY_NAME.name(), count);
        log.info(Counters.EVENT_ACCESSED_BY_NAME + ": " + count + " times");
    }

    @AfterReturning("pointcutPriceQueried()")
    public void countEventPriceQueried() {
        Integer count = counterRepository.getByName(Counters.PRICE_QUERIED.name()) + 1;
        counterRepository.save(Counters.PRICE_QUERIED.name(), count);
        log.info(Counters.PRICE_QUERIED + ": " + count + " times");
    }

    @AfterReturning("pointcutTicketBooked()")
    public void countTicketBooked() {
        Integer count = counterRepository.getByName(Counters.TICKET_BOOKED.name()) + 1;
        counterRepository.save(Counters.TICKET_BOOKED.name(), count);
        log.info(Counters.TICKET_BOOKED + ": " + count + " times");
    }
}
