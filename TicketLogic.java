package com.moviemanagerexam.ticketgui.bll;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.moviemanagerexam.ticketgui.be.Ticket;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TicketLogic {

    public BufferedImage generateQRCode(String text, int width, int height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public BufferedImage generateBarcode(String text, int width, int height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public void generateTicketPDF(Ticket ticket, String filePath) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("EVENT TICKET: " + ticket.getEvent().getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Customer: " + ticket.getCustomerName());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Email: " + ticket.getCustomerEmail());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Type: " + ticket.getType());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Location: " + ticket.getEvent().getLocation());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Time: " + ticket.getEvent().getStartDateTime().toString());
                contentStream.endText();

                // Add QR Code
                BufferedImage qrImage = generateQRCode(ticket.getUuid(), 150, 150);
                PDImageXObject pdQrImage = LosslessFactory.createFromImage(document, qrImage);
                contentStream.drawImage(pdQrImage, 50, 500, 150, 150);

                // Add Barcode
                BufferedImage barcodeImage = generateBarcode(ticket.getUuid(), 300, 50);
                PDImageXObject pdBarcodeImage = LosslessFactory.createFromImage(document, barcodeImage);
                contentStream.drawImage(pdBarcodeImage, 50, 440, 300, 50);
            }

            document.save(filePath);
        }
    }
}
