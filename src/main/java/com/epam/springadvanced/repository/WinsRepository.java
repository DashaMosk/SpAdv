package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.Win;

import java.util.List;

public interface WinsRepository {
    Win save(Win win);
    List<Win> getAll();
    List<Win> getByUserId(long userId);
    void delete(long userId);
}
