package com.moviemanagerexam.ticketgui;



import com.moviemanagerexam.ticketgui.be.Event;
import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.bll.BLLFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class MainController {
    private static final DateTimeFormatter EVENT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnDashboard, btnEvents, btnUsers, btnSpecialTickets;

    private final BLLFacade bllFacade = new BLLFacade();
    private List<Button> navButtons;
    private User currentUser;

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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if (contentArea != null) {
            loadDashboard();
        }
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
        root.getChildren().add(createHeaderText("Welcome back, " + getDisplayName()));

        FlowPane stats = new FlowPane(20, 20);
        stats.getChildren().addAll(
                createStatCard("Total Events", Integer.toString(loadEvents().size())),
                createStatCard("Users", Integer.toString(loadUsers().size())),
                createStatCard("Role", currentUser == null ? "Guest" : formatRole(currentUser.getRole()))
        );
        root.getChildren().add(stats);

        root.getChildren().add(createHeaderText("Upcoming Events"));

        VBox activity = new VBox(10);
        List<Event> events = loadEvents();
        if (events.isEmpty()) {
            activity.getChildren().add(createActivityItem("No events available yet.", "Create one from Events"));
        } else {
            for (Event event : events) {
                activity.getChildren().add(createActivityItem(
                        event.getName() + " at " + event.getLocation(),
                        formatEventTime(event)
                ));
            }
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
        Text title = createHeaderText("Events Management");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnAdd = new Button("Create Event");
        btnAdd.getStyleClass().add("action-button");
        header.getChildren().addAll(title, spacer, btnAdd);

        root.getChildren().add(header);

        FlowPane eventFlow = new FlowPane(20, 20);
        List<Event> events = loadEvents();
        if (events.isEmpty()) {
            eventFlow.getChildren().add(createActivityItem("No events found.", "Check your data source"));
        } else {
            for (Event event : events) {
                eventFlow.getChildren().add(createEventCard(
                        event.getName(),
                        event.getLocation(),
                        formatEventTime(event)
                ));
            }
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
        root.getChildren().add(createHeaderText("User Management"));

        TableView<User> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> colUsername = new TableColumn<>("Username");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> colRole = new TableColumn<>("Role");
        colRole.setCellValueFactory(cellData ->
                new SimpleStringProperty(formatRole(cellData.getValue().getRole())));

        table.getColumns().addAll(colId, colUsername, colRole);
        table.getItems().addAll(loadUsers());
        table.setPlaceholder(new Label("No users found."));

        root.getChildren().add(table);
        contentArea.getChildren().add(root);
    }

    private void loadSpecialTicketsView() {
        contentArea.getChildren().clear();

        VBox root = new VBox(20);
        root.getChildren().add(createHeaderText("Special Vouchers & Tickets"));

        FlowPane ticketFlow = new FlowPane(20, 20);
        ticketFlow.getChildren().addAll(
                createTicketCard("Free Beer", "One-time use at any event"),
                createTicketCard("50% Off Drink", "Valid for Summer Festival"),
                createTicketCard("Earplugs", "Safety first!")
        );

        root.getChildren().add(ticketFlow);
        contentArea.getChildren().add(root);
    }

    private List<Event> loadEvents() {
        try {
            return bllFacade.getAllEvents();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<User> loadUsers() {
        try {
            return bllFacade.getAllUsers();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private String getDisplayName() {
        return currentUser == null ? "Administrator" : currentUser.getUsername();
    }

    private String formatRole(User.Role role) {
        return role.name().replace('_', ' ');
    }

    private String formatEventTime(Event event) {
        return event.getStartDateTime() == null
                ? "Time not set"
                : event.getStartDateTime().format(EVENT_TIME_FORMAT);
    }

    private Text createHeaderText(String value) {
        Text text = new Text(value);
        text.getStyleClass().add("header-text");
        return text;
    }

    private Node createStatCard(String title, String value) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPrefWidth(250);

        Text titleText = new Text(title);
        titleText.setFill(javafx.scene.paint.Color.web("#a6adc8"));

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");

        card.getChildren().addAll(titleText, valueLabel);
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
        Text timestamp = new Text(time);
        timestamp.setFill(javafx.scene.paint.Color.web("#6c7086"));
        timestamp.setStyle("-fx-font-size: 11px;");

        content.getChildren().addAll(main, timestamp);
        item.getChildren().add(content);
        return item;
    }

    private Node createEventCard(String name, String loc, String time) {
        VBox card = new VBox(15);
        card.getStyleClass().add("event-card");
        card.setPrefWidth(300);

        Text nameText = new Text(name);
        nameText.getStyleClass().add("header-text");
        nameText.setStyle("-fx-font-size: 18px;");

        VBox info = new VBox(5);
        Text locationText = new Text("Location: " + loc);
        locationText.setFill(javafx.scene.paint.Color.web("#a6adc8"));
        Text timeText = new Text("Time: " + time);
        timeText.setFill(javafx.scene.paint.Color.web("#a6adc8"));
        info.getChildren().addAll(locationText, timeText);

        HBox actions = new HBox(10);
        Button btnEdit = new Button("Edit");
        btnEdit.getStyleClass().add("secondary-button");
        Button btnTickets = new Button("Tickets");
        btnTickets.getStyleClass().add("action-button");
        actions.getChildren().addAll(btnEdit, btnTickets);

        card.getChildren().addAll(nameText, info, actions);
        return card;
    }

    private Node createTicketCard(String type, String desc) {
        VBox card = new VBox(10);
        card.getStyleClass().add("ticket-card");
        card.setPrefWidth(200);
        card.setAlignment(Pos.CENTER);

        Text title = new Text(type);
        title.getStyleClass().add("header-text");
        title.setStyle("-fx-font-size: 16px;");

        Text description = new Text(desc);
        description.setFill(javafx.scene.paint.Color.web("#a6adc8"));
        description.setWrappingWidth(180);
        description.setTextAlignment(TextAlignment.CENTER);

        VBox barcodeSim = new VBox();
        barcodeSim.setPrefSize(150, 40);
        barcodeSim.setStyle("-fx-background-color: white; -fx-padding: 5;");

        HBox lines = new HBox(2);
        for (int i = 0; i < 20; i++) {
            Region line = new Region();
            line.setPrefWidth((i % 4) + 2);
            line.setPrefHeight(30);
            line.setStyle("-fx-background-color: black;");
            lines.getChildren().add(line);
        }
        barcodeSim.getChildren().add(lines);

        Button btnPrint = new Button("Print");
        btnPrint.getStyleClass().add("action-button");

        card.getChildren().addAll(title, description, barcodeSim, btnPrint);
        return card;
    }
}
