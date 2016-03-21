package com.epam.springadvanced.service.impl;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.TicketRepository;
import com.epam.springadvanced.repository.UserRepository;
import com.epam.springadvanced.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(String name, String email, LocalDate birthDay) {
        return new User(name, email, birthDay);
    }

    @Override
    public User register(User user) {
        log.info("User <" + user.getName() + "> is registered");
        user = userRepository.save(user);
        return user;
    }

    @Override
    public void remove(User user) {
        Optional.ofNullable(user).ifPresent(u -> userRepository.delete(u.getId()));
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Collection<Ticket> getBookedTickets() {
        return ticketRepository.getBookedTickets();
    }

    @Override
    public Collection<Ticket> getBookedTicketsByUserId(long userId) {
        return ticketRepository.getBookedTicketsByUserId(userId);
    }
}
