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

    private BLLFacade bllFacade = new BLLFacade();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            User user = bllFacade.login(username, password);
            if (user != null) {
                // Navigate based on role
                loadMainView(user);
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during login: " + e.getMessage());
        }
    }

    private void loadMainView(User user) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moviemanagerexam/ticketgui/main-view.fxml"));
        Parent root = loader.load();
        
        // Passing user to controller if needed (refactored MainController)
        // ...

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
