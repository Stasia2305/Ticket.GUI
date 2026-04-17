package com.moviemanagerexam.ticketgui.gui.controller;

import com.moviemanagerexam.ticketgui.be.User;
import com.moviemanagerexam.ticketgui.bll.BLLFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private final BLLFacade bllFacade = new BLLFacade();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert("Missing information", "Please enter both username and password.");
            return;
        }

        try {
            User user = bllFacade.login(username, password);
            if (user != null) {
                loadMainView(user);
            } else {
                showAlert("Login failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showAlert("Login error", "An error occurred during login: " + e.getMessage());
        }
    }

    private void loadMainView(User user) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moviemanagerexam/ticketgui/main-view.fxml"));
        Parent root = loader.load();

        Object controller = loader.getController();
        if (controller instanceof com.moviemanagerexam.ticketgui.MainController mainController) {
            mainController.setCurrentUser(user);
        }

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
