module com.moviemanagerexam.ticketgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.naming;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires org.apache.pdfbox;
    requires com.microsoft.sqlserver.jdbc;

    opens com.moviemanagerexam.ticketgui to javafx.fxml;
    opens com.moviemanagerexam.ticketgui.gui.controller to javafx.fxml;
    exports com.moviemanagerexam.ticketgui;
}
