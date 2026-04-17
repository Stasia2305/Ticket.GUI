package com.moviemanagerexam.ticketgui.be;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String locationGuidance;
    private String notes;

    public Event(int id, String name, LocalDateTime startDateTime, String location) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime;
        this.location = location;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }
    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getLocationGuidance() { return locationGuidance; }
    public void setLocationGuidance(String locationGuidance) { this.locationGuidance = locationGuidance; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return name;
    }
}
