package com.moviemanagerexam.ticketgui.be;

import java.util.UUID;

public class Ticket {
    private int id;
    private String uuid;
    private String customerName;
    private String customerEmail;
    private Event event;
    private String type;

    public Ticket(int id, String customerName, String customerEmail, Event event, String type) {
        this.id = id;
        this.uuid = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.event = event;
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
