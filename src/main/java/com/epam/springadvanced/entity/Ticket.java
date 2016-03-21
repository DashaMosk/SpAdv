package com.epam.springadvanced.entity;

import java.io.Serializable;

public class Ticket implements Serializable{
    private Long id;
    private float price;
    private Seat seat;
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", seat=" + seat +
                ", event=" + event.getId() +
                '}';
    }
}
