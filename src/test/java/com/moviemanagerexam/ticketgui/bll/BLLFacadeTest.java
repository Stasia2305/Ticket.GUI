package com.moviemanagerexam.ticketgui.bll;

import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.dal.mock.MockEventDAO;
import com.moviemanagerexam.ticketgui.dal.mock.MockUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BLLFacadeTest {

    private BLLFacade bllFacade;

    @BeforeEach
    public void setUp() {
        bllFacade = new BLLFacade(new MockUserDAO(), new MockEventDAO(), new TicketLogic());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = bllFacade.login("admin", "admin");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals(User.Role.ADMIN, user.getRole());
    }

    @Test
    public void testLoginFailure() throws Exception {
        User user = bllFacade.login("admin", "wrongpassword");
        assertNull(user);
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        String newUsername = "newuser";
        User user = bllFacade.createUser(newUsername, "password", User.Role.EVENT_COORDINATOR);
        
        assertNotNull(user);
        assertEquals(newUsername, user.getUsername());
        
        // Verify user is in the list
        List<User> users = bllFacade.getAllUsers();
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals(newUsername)));
    }

    @Test
    public void testCreateUserDuplicateFailure() {
        // "admin" already exists in MockUserDAO
        Exception exception = assertThrows(Exception.class, () -> {
            bllFacade.createUser("admin", "password", User.Role.EVENT_COORDINATOR);
        });
        
        assertEquals("Username already exists.", exception.getMessage());
    }
}
