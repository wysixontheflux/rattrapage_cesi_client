package com.enzo.fxapp.Controllers.Admin;

import com.enzo.fxapp.Controllers.Client.Collaborateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.GridPane;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminController {

    @FXML
    private TableView<Collaborateur> adminTableView;
    @FXML
    private TableColumn<Collaborateur, String> adminNameColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminFirstNameColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminPhoneColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminMobileColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminEmailColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminServiceColumn;
    @FXML
    private TableColumn<Collaborateur, String> adminSiteColumn;

    private ObservableList<Collaborateur> collaborators = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configuration des colonnes du tableau
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adminFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adminPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneFixe"));
        adminMobileColumn.setCellValueFactory(new PropertyValueFactory<>("telephonePortable"));
        adminEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adminServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        adminSiteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));

        loadCollaborators();
    }

    @FXML
    private void handleAddCollaborateur() {
        Optional<Collaborateur> result = showCollaboratorDialog(null);
        result.ifPresent(collaborator -> {
            if (sendCollaboratorToApi(collaborator, "POST", "http://localhost:8080/api/employees/create")) {
                collaborators.add(collaborator);
                adminTableView.refresh();
            }
        });
    }

    @FXML
    private void handleEditCollaborateur() {
        Collaborateur selectedCollaborator = adminTableView.getSelectionModel().getSelectedItem();
        if (selectedCollaborator != null) {
            Optional<Collaborateur> result = showCollaboratorDialog(selectedCollaborator);
            result.ifPresent(collaborator -> {
                collaborator.setId(selectedCollaborator.getId());
                if (sendCollaboratorToApi(collaborator, "PATCH", "http://localhost:8080/api/employees/modify")) {
                    int index = collaborators.indexOf(selectedCollaborator);
                    collaborators.set(index, collaborator);
                    adminTableView.refresh();
                }
            });
        } else {
            showAlert("Erreur", "Aucun collaborateur sélectionné", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteCollaborateur() {
        Collaborateur selectedCollaborator = adminTableView.getSelectionModel().getSelectedItem();
        if (selectedCollaborator != null) {
            if (sendCollaboratorToApi(selectedCollaborator, "DELETE", "http://localhost:8080/api/employees/delete")) {
                collaborators.remove(selectedCollaborator);
                adminTableView.refresh();
            }
        } else {
            showAlert("Erreur", "Aucun collaborateur sélectionné", Alert.AlertType.ERROR);
        }
    }

    private void loadCollaborators() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/employees/listall"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> apiResponse = mapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> data = (Map<String, Object>) apiResponse.get("data");
            List<Map<String, Object>> employees = (List<Map<String, Object>>) data.get("body");

            for (Map<String, Object> employee : employees) {
                Integer id = employee.get("id") != null ? Integer.parseInt(employee.get("id").toString()) : null;
                String nom = employee.get("nom") != null ? employee.get("nom").toString() : "";
                String prenom = employee.get("prenom") != null ? employee.get("prenom").toString() : "";
                String telephoneFixe = employee.get("telephone_fixe") != null ? employee.get("telephone_fixe").toString() : "";
                String telephonePortable = employee.get("telephone_portable") != null ? employee.get("telephone_portable").toString() : "";
                String email = employee.get("email") != null ? employee.get("email").toString() : "";
                String service = employee.get("service") != null ? employee.get("service").toString() : "";
                String site = employee.get("site") != null ? employee.get("site").toString() : "";

                System.out.println("Fetched employee with fixed phone: " + telephoneFixe + " and mobile phone: " + telephonePortable);

                Collaborateur collaborateur = new Collaborateur(id, nom, prenom, telephoneFixe, telephonePortable, email, service, site);
                collaborators.add(collaborateur);
            }
            adminTableView.setItems(collaborators);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les collaborateurs", Alert.AlertType.ERROR);
        }
    }

    private boolean sendCollaboratorToApi(Collaborateur collaborator, String method, String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(collaborator);
            System.out.println("Sending collaborator with fixed phone: " + collaborator.getTelephoneFixe() + " and mobile phone: " + collaborator.getTelephonePortable());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method(method, HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("API response status code: " + response.statusCode());
            System.out.println("API response body: " + response.body());
            return response.statusCode() == 200 || response.statusCode() == 201;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la communication avec l'API", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<Collaborateur> showCollaboratorDialog(Collaborateur collaborator) {
        Dialog<Collaborateur> dialog = new Dialog<>();
        dialog.setTitle(collaborator == null ? "Ajouter un collaborateur" : "Modifier le collaborateur");

        // Set the button types
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the fields
        TextField nomField = new TextField(collaborator != null ? collaborator.getNom() : "");
        TextField prenomField = new TextField(collaborator != null ? collaborator.getPrenom() : "");
        TextField telephoneFixeField = new TextField(collaborator != null ? collaborator.getTelephoneFixe() : "");
        TextField telephonePortableField = new TextField(collaborator != null ? collaborator.getTelephonePortable() : "");
        TextField emailField = new TextField(collaborator != null ? collaborator.getEmail() : "");
        TextField serviceField = new TextField(collaborator != null ? collaborator.getService() : "");
        TextField siteField = new TextField(collaborator != null ? collaborator.getSite() : "");

        GridPane grid = new GridPane();
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Téléphone Fixe:"), 0, 2);
        grid.add(telephoneFixeField, 1, 2);
        grid.add(new Label("Téléphone Portable:"), 0, 3);
        grid.add(telephonePortableField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Service:"), 0, 5);
        grid.add(serviceField, 1, 5);
        grid.add(new Label("Site:"), 0, 6);
        grid.add(siteField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a collaborator when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                System.out.println("Collected phone numbers: Fixed - " + telephoneFixeField.getText() + ", Mobile - " + telephonePortableField.getText());
                return new Collaborateur(
                        collaborator != null ? collaborator.getId() : null,
                        nomField.getText(),
                        prenomField.getText(),
                        telephoneFixeField.getText(),
                        telephonePortableField.getText(),
                        emailField.getText(),
                        serviceField.getText(),
                        siteField.getText()
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
