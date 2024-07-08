package com.enzo.fxapp.Controllers.Client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientController {

    @FXML
    private TextField searchField;

    @FXML
    protected void handleSearchButtonAction() {
        String searchQuery = searchField.getText();
        System.out.println("Recherche pour : " + searchQuery);
    }
}
