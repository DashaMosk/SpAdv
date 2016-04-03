package com.epam.springadvanced.entity;
import java.io.Serializable;

public class UserAccount implements Serializable {

    private Long id;
    private User user;
    private float amount;

    public UserAccount(){}

    public UserAccount(Long id, User user, float amount) {
        this.id = id;
        this.user = user;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
