package com.enzo.fxapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.enzo.fxapp.App;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Map pour stocker les utilisateurs et leurs informations
    private final Map<String, User> users = new HashMap<>();

    private App app;

    public LoginController() {
        // Ajout des utilisateurs en dur dans le constructeur
        users.put("admin@cesi.fr", new User("admin@cesi.fr", "enzoenzo", 1)); // Admin
        users.put("visiteur@cesi.fr", new User("visiteur@cesi.fr", "enzoenzo", 2)); // Visiteur
    }

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            showAlert("Login Successful", "Vous êtes connecté en tant que : " + (user.getRole() == 1 ? "Admin" : "Visiteur"));
            app.handleSuccessfulLogin(user.getRole());
        } else {
            showAlert("Login Failed", "Mauvais email ou mot de passe");
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe interne pour gérer les utilisateurs
    private static class User {
        private final String username;
        private final String password;
        private final int role;

        User(String username, String password, int role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        String getPassword() {
            return password;
        }

        int getRole() {
            return role;
        }
    }
}
