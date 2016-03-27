package com.epam.springadvanced.entity;

import com.epam.springadvanced.service.Roles;

import java.io.Serializable;

public class Role implements Serializable{
    private int id;
    private Roles name;

    public Role(){}

    public Role(int id, Roles name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }
}
