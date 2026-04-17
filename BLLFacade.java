package com.moviemanagerexam.ticketgui.bll;

import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.be.Ticket;
import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.dal.DBConnector;
import com.moviemanagerexam.ticketgui.dal.IEventDAO;
import com.moviemanagerexam.ticketgui.dal.IUserDAO;
import com.moviemanagerexam.ticketgui.dal.database.EventDAO;
import com.moviemanagerexam.ticketgui.dal.database.UserDAO;
import com.moviemanagerexam.ticketgui.dal.mock.MockEventDAO;
import com.moviemanagerexam.ticketgui.dal.mock.MockUserDAO;

import java.awt.image.BufferedImage;
import java.util.List;

public class BLLFacade {
    private final IUserDAO userDAO;
    private final IEventDAO eventDAO;
    private final TicketLogic ticketLogic;

    public BLLFacade() {
        DBConnector connector = new DBConnector();
        this.userDAO = new UserDAO(connector);
        this.eventDAO = new EventDAO(connector);
        this.ticketLogic = new TicketLogic();
    }

    public BLLFacade(IUserDAO userDAO, IEventDAO eventDAO, TicketLogic ticketLogic) {
        this.userDAO = userDAO;
        this.eventDAO = eventDAO;
        this.ticketLogic = ticketLogic;
    }

    public User login(String username, String password) throws Exception {
        return userDAO.login(username, password);
    }

    public User createUser(String username, String password, User.Role role) throws Exception {
        return userDAO.createUser(username, password, role);
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
