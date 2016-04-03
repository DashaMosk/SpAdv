package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.UserAccount;

public interface AccountRepository {
    UserAccount getByUserId(long userId);
    UserAccount save(UserAccount account);
    void delete(long userId);
}
