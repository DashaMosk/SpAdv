package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.User;

import java.util.Collection;

public interface UserRepository {
    User save(User user);
    void delete(long id);
    User findById(long id);
    User findByEmail(String email);
    User findByName(String name);

    Collection<User> getAll();
}
