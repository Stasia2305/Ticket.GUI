package com.moviemanagerexam.ticketgui.bll;

import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.be.Ticket;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TicketLogicTest {

    @Test
    public void testGenerateQRCode() throws Exception {
        TicketLogic logic = new TicketLogic();
        BufferedImage image = logic.generateQRCode("test-uuid", 100, 100);
        assertNotNull(image);
        assertEquals(100, image.getWidth());
        assertEquals(100, image.getHeight());
    }

    @Test
    public void testGenerateBarcode() throws Exception {
        TicketLogic logic = new TicketLogic();
        BufferedImage image = logic.generateBarcode("test-uuid", 200, 50);
        assertNotNull(image);
        assertEquals(200, image.getWidth());
        assertEquals(50, image.getHeight());
    }
}
