package com.enzo.fxapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
    }
}
