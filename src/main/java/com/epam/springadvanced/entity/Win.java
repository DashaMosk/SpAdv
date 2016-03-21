package com.epam.springadvanced.entity;

import java.io.Serializable;
import java.util.Date;

public class Win implements Serializable{
    private Long id;
    private User user;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
