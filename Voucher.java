package com.moviemanagerexam.ticketgui.be;

import java.util.UUID;

public class Voucher {
    private int id;
    private String uuid;
    private String title;
    private String description;
    private Event event; // null if valid for all events

    public Voucher(int id, String title, String description, Event event) {
        this.id = id;
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.event = event;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}
