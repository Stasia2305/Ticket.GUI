package com.moviemanagerexam.ticketgui.bll;

import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.be.Ticket;
import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.dal.IEventDAO;
import com.moviemanagerexam.ticketgui.dal.IUserDAO;
import com.moviemanagerexam.ticketgui.dal.mock.MockEventDAO;
import com.moviemanagerexam.ticketgui.dal.mock.MockUserDAO;

import java.awt.image.BufferedImage;
import java.util.List;

public class BLLFacade {
    private IUserDAO userDAO;
    private IEventDAO eventDAO;
    private TicketLogic ticketLogic;

    public BLLFacade() {
        userDAO = new MockUserDAO();
        eventDAO = new MockEventDAO();
        ticketLogic = new TicketLogic();
    }

    public User login(String username, String password) throws Exception {
        return userDAO.login(username, password);
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }

    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }

    public BufferedImage generateQRCode(String text, int width, int height) throws Exception {
        return ticketLogic.generateQRCode(text, width, height);
    }
    
    public void generateTicketPDF(Ticket ticket, String filePath) throws Exception {
        ticketLogic.generateTicketPDF(ticket, filePath);
    }
}
