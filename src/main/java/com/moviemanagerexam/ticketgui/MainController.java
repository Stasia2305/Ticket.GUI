package com.moviemanagerexam.ticketgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private VBox activityList;

    @FXML
    private Button btnDashboard, btnEvents, btnUsers, btnSpecialTickets;

    private List<Button> navButtons;

    @FXML
    public void initialize() {
        navButtons = List.of(btnDashboard, btnEvents, btnUsers, btnSpecialTickets);
        updateActiveButton(btnDashboard);
        loadDashboard();
    }

    @FXML
    private void handleDashboard(ActionEvent event) {
        updateActiveButton((Button) event.getSource());
        loadDashboard();
    }

    @FXML
    private void handleEvents(ActionEvent event) {
        updateActiveButton((Button) event.getSource());
        loadEventsView();
    }

    @FXML
    private void handleUsers(ActionEvent event) {
        updateActiveButton((Button) event.getSource());
        loadUsersView();
    }

    @FXML
    private void handleSpecialTickets(ActionEvent event) {
        updateActiveButton((Button) event.getSource());
        loadSpecialTicketsView();
    }

    private void updateActiveButton(Button activeBtn) {
        for (Button btn : navButtons) {
            btn.getStyleClass().remove("active");
        }
        activeBtn.getStyleClass().add("active");
    }

    private void loadDashboard() {
        contentArea.getChildren().clear();
        
        VBox root = new VBox(20);
        root.getChildren().add(new Text("Welcome back, Administrator") {{ getStyleClass().add("header-text"); }});
        
        FlowPane stats = new FlowPane(20, 20);
        stats.getChildren().addAll(
            createStatCard("Total Events", "24"),
            createStatCard("Tickets Sold", "1,248"),
            createStatCard("Active Coordinators", "8")
        );
        root.getChildren().add(stats);
        
        root.getChildren().add(new Text("Recent Activity") {{ getStyleClass().add("header-text"); }});
        
        VBox activity = new VBox(10);
        for (int i = 0; i < 5; i++) {
            activity.getChildren().add(createActivityItem("Coordinator John Doe created event 'Rock Concert'", "2 hours ago"));
        }
        
        ScrollPane scroll = new ScrollPane(activity);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        root.getChildren().add(scroll);
        
        contentArea.getChildren().add(root);
    }

    private void loadEventsView() {
        contentArea.getChildren().clear();
        
        VBox root = new VBox(20);
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Text title = new Text("Events Management");
        title.getStyleClass().add("header-text");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnAdd = new Button("+ Create Event");
        btnAdd.getStyleClass().add("action-button");
        header.getChildren().addAll(title, spacer, btnAdd);
        
        root.getChildren().add(header);
        
        TextField search = new TextField();
        search.setPromptText("Search events...");
        search.getStyleClass().add("text-input");
        root.getChildren().add(search);
        
        FlowPane eventFlow = new FlowPane(20, 20);
        for (int i = 1; i <= 6; i++) {
            eventFlow.getChildren().add(createEventCard("Summer Festival " + i, "Central Park", "2024-07-15 18:00"));
        }
        
        ScrollPane scroll = new ScrollPane(eventFlow);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        root.getChildren().add(scroll);
        
        contentArea.getChildren().add(root);
    }

    private void loadUsersView() {
        contentArea.getChildren().clear();
        VBox root = new VBox(20);
        root.getChildren().add(new Text("User Management") {{ getStyleClass().add("header-text"); }});
        
        TableView<String[]> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<String[], String> colName = new TableColumn<>("Name");
        TableColumn<String[], String> colEmail = new TableColumn<>("Email");
        TableColumn<String[], String> colRole = new TableColumn<>("Role");
        
        table.getColumns().addAll(colName, colEmail, colRole);
        
        root.getChildren().add(table);
        contentArea.getChildren().add(root);
    }

    private void loadSpecialTicketsView() {
        contentArea.getChildren().clear();
        VBox root = new VBox(20);
        root.getChildren().add(new Text("Special Vouchers & Tickets") {{ getStyleClass().add("header-text"); }});
        
        FlowPane ticketFlow = new FlowPane(20, 20);
        ticketFlow.getChildren().addAll(
            createTicketCard("Free Beer", "One-time use at any event"),
            createTicketCard("50% Off Drink", "Valid for Summer Festival"),
            createTicketCard("Earplugs", "Safety first!")
        );
        
        root.getChildren().add(ticketFlow);
        contentArea.getChildren().add(root);
    }

    private Node createStatCard(String title, String value) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPrefWidth(250);
        
        Text t = new Text(title);
        t.setFill(javafx.scene.paint.Color.web("#a6adc8"));
        
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");
        
        card.getChildren().addAll(t, v);
        return card;
    }

    private Node createActivityItem(String text, String time) {
        HBox item = new HBox(15);
        item.getStyleClass().add("card");
        item.setPadding(new Insets(10, 15, 10, 15));
        item.setAlignment(Pos.CENTER_LEFT);
        
        VBox content = new VBox(2);
        Text main = new Text(text);
        main.setFill(javafx.scene.paint.Color.web("#cdd6f4"));
        Text t = new Text(time);
        t.setFill(javafx.scene.paint.Color.web("#6c7086"));
        t.setStyle("-fx-font-size: 11px;");
        
        content.getChildren().addAll(main, t);
        item.getChildren().add(content);
        return item;
    }

    private Node createEventCard(String name, String loc, String time) {
        VBox card = new VBox(15);
        card.getStyleClass().add("event-card");
        card.setPrefWidth(300);
        
        Text n = new Text(name);
        n.getStyleClass().add("header-text");
        n.setStyle("-fx-font-size: 18px;");
        
        VBox info = new VBox(5);
        info.getChildren().addAll(
            new Text("📍 " + loc) {{ setFill(javafx.scene.paint.Color.web("#a6adc8")); }},
            new Text("⏰ " + time) {{ setFill(javafx.scene.paint.Color.web("#a6adc8")); }}
        );
        
        HBox actions = new HBox(10);
        Button btnEdit = new Button("Edit");
        btnEdit.getStyleClass().add("secondary-button");
        Button btnTickets = new Button("Tickets");
        btnTickets.getStyleClass().add("action-button");
        actions.getChildren().addAll(btnEdit, btnTickets);
        
        card.getChildren().addAll(n, info, actions);
        return card;
    }

    private Node createTicketCard(String type, String desc) {
        VBox card = new VBox(10);
        card.getStyleClass().add("ticket-card");
        card.setPrefWidth(200);
        card.setAlignment(Pos.CENTER);
        
        Text t = new Text(type);
        t.getStyleClass().add("header-text");
        t.setStyle("-fx-font-size: 16px;");
        
        Text d = new Text(desc);
        d.setFill(javafx.scene.paint.Color.web("#a6adc8"));
        d.setWrappingWidth(180);
        d.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        VBox barcodeSim = new VBox();
        barcodeSim.setPrefSize(150, 40);
        barcodeSim.setStyle("-fx-background-color: white; -fx-padding: 5;");
        // Simulate barcode lines
        HBox lines = new HBox(2);
        for(int i=0; i<20; i++) {
            Region r = new Region();
            r.setPrefWidth(Math.random() * 5 + 1);
            r.setPrefHeight(30);
            r.setStyle("-fx-background-color: black;");
            lines.getChildren().add(r);
        }
        barcodeSim.getChildren().add(lines);
        
        Button btnPrint = new Button("Print");
        btnPrint.getStyleClass().add("action-button");
        
        card.getChildren().addAll(t, d, barcodeSim, btnPrint);
        return card;
    }
}
