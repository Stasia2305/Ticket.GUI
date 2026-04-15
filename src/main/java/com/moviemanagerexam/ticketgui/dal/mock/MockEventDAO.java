package com.moviemanagerexam.ticketgui.dal.mock;

import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.dal.IEventDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockEventDAO implements IEventDAO {
    private List<Event> events = new ArrayList<>();

    public MockEventDAO() {
        events.add(new Event(1, "EASV Party", LocalDateTime.now().plusDays(7), "EASV Campus"));
        events.add(new Event(2, "Wine Tasting", LocalDateTime.now().plusDays(14), "Esbjerg City Center"));
    }

    @Override
    public List<Event> getAllEvents() {
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        event.setId(events.size() + 1);
        events.add(event);
        return event;
    }

    @Override
    public void updateEvent(Event event) {
        // Mock update
    }

    @Override
    public void deleteEvent(Event event) {
        events.remove(event);
    }
}
