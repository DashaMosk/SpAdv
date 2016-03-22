package com.epam.springadvanced.web;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.repository.AuditoriumRepository;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.service.Rating;
import com.epam.springadvanced.service.exception.AuditoriumAlreadyAssignedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@RequestMapping("/bookingservice")
public class EventsController {
    @Autowired
    EventService eventService;
    @Autowired
    AuditoriumRepository auditoriumService;

    @RequestMapping(path="/event/create", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Event created.")
    public void createEvent(@RequestParam final String name, @RequestParam final float price,
                            @RequestParam final Rating rating) {
        eventService.create(name, price, rating);
    }

    @RequestMapping(path = "/event/remove", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK, reason = "Event removed.")
    public void removeUser(@RequestParam final long eventId){
        eventService.remove(eventService.getById(eventId));
    }

    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    @ResponseBody
    public Event getEventById(@PathVariable final long eventId) {
        return  eventService.getById(eventId);
    }

    @RequestMapping(value = "/event/byname", method = RequestMethod.GET)
    @ResponseBody
    public Event getEventByName(@RequestParam final String name) {
        return eventService.getByName(name);
    }

    @RequestMapping(path = "/event/events", method = RequestMethod.GET)
    public String getAllEvent(ModelMap model){
        Collection<Event> events = eventService.getAll();
        model.addAttribute("events", events);
        model.addAttribute("title", "All events");
        return "events";
    }

    @RequestMapping(path = "/event/nextevents", method = RequestMethod.GET)
    public String getNextEvent(ModelMap model, @RequestParam final LocalDateTime to){
        Collection<Event> events = eventService.getNextEvents(to);
        model.addAttribute("events", events);
        model.addAttribute("title", "Next events");
        return "events";
    }

    @RequestMapping(path = "/event/rangeevents", method = RequestMethod.GET)
    public String getEventsForDates(ModelMap model, @RequestParam final LocalDateTime from, @RequestParam final LocalDateTime to){
        Collection<Event> events = eventService.getForDateRange(from, to);
        model.addAttribute("events", events);
        model.addAttribute("title", "Next events");
        return "events";
    }

    @RequestMapping(path = "/event/assignauditorium", method = RequestMethod.POST)
    @ResponseBody
    public Event assignAuditorium(@RequestParam final long eventId, @RequestParam final int auditId,
                                  @RequestParam final LocalDateTime date) throws AuditoriumAlreadyAssignedException {
        return eventService.assignAuditorium(eventService.getById(eventId), auditoriumService.getById(auditId), date);
    }

}
