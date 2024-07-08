package com.enzo.fxapp.Controllers.Client;

import com.enzo.fxapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainLayoutController {

    @FXML
    private BorderPane mainPane;

    private App app;  // Référence à l'application principale

    public void setApp(App app) {
        this.app = app;  // Setter pour définir l'instance de App
    }

    @FXML
    private void handleHome() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Client/Home.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    private void handleCollaborateur() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Client/Collaborateur.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    private void handleParametre() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Client/Parametre.fxml"));
        mainPane.setCenter(pane);
    }

    @FXML
    private void handleSeDeconnecter() {
        // Fermer la fenêtre principale et ouvrir la page de login
        app.openLoginPage();
    }
}
