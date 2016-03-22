package com.epam.springadvanced.service.impl;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.service.Rating;
import com.epam.springadvanced.service.UploadService;
import com.epam.springadvanced.service.UserService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;

    private static String OUTPUT_LOCATION="C:/mytemp/";

    public void uploadFile(MultipartFile file) throws IOException {
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createParser(file.getInputStream());
//      File jp = new File(OUTPUT_LOCATION + file.getOriginalFilename());
//      FileCopyUtils.copy(file.getBytes(), jp);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(file.getOriginalFilename().equals("users.json")) {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            User[] users = mapper.readValue(jp, User[].class);
            for(User user : users) {
                User newUser = userService.create(user.getName(), user.getEmail(), user.getBirthday());
                userService.register(newUser);
            }
        }
        if(file.getOriginalFilename().equals("events.json")) {
            Event[] events = mapper.readValue(jp, Event[].class);
            for(Event event : events) {
                if(event.getRating() == null){
                    event.setRating(Rating.HIGH);
                }
                eventService.create(event.getName(), event.getDateTime(), event.getTicketPrice(), event.getRating());
            }
        }
    }
}
