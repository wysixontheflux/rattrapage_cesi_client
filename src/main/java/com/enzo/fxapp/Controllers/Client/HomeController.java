package com.enzo.fxapp.Controllers.Client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HomeController {

    @FXML
    private ListView<String> listSites;

    @FXML
    private ListView<String> listJobs;

    @FXML
    private void initialize() {
        // Initialiser les listes avec des données
        listSites.getItems().addAll("Paris - Siège administratif", "Nantes - Site de Production", "Toulouse - Site de Production", "Nice - Site de Production", "Lille - Site de Production");
        listJobs.getItems().addAll("Comptabilité", "Production", "Accueil", "Informatique", "Commercial");
    }

    @FXML
    private void handleMoreInfo() {
        // Gérer l'action pour le bouton 'Plus d'infos'
        System.out.println("Plus d'infos cliqué!");
    }
}
