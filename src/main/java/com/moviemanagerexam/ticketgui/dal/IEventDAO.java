package com.moviemanagerexam.ticketgui.dal;

import com.moviemanagerexam.ticketgui.be.Event;
import java.util.List;

public interface IEventDAO {
    List<Event> getAllEvents() throws Exception;
    Event createEvent(Event event) throws Exception;
    void updateEvent(Event event) throws Exception;
    void deleteEvent(Event event) throws Exception;
}
