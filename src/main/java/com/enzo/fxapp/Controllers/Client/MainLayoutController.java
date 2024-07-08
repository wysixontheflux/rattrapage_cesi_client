package com.enzo.fxapp.Controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainLayoutController {

    @FXML
    private BorderPane mainPane;

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
        // Logique de déconnexion
    }
}
