package com.enzo.fxapp.Controllers.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CollaborateurController {

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> siteComboBox;
    @FXML
    private ComboBox<String> serviceComboBox;
    @FXML
    private TableView<Collaborateur> collaboratorTableView;
    @FXML
    private TableColumn<Collaborateur, String> nameColumn;
    @FXML
    private TableColumn<Collaborateur, String> firstNameColumn;
    @FXML
    private TableColumn<Collaborateur, String> phoneColumn;
    @FXML
    private TableColumn<Collaborateur, String> mobileColumn;
    @FXML
    private TableColumn<Collaborateur, String> emailColumn;
    @FXML
    private TableColumn<Collaborateur, String> serviceColumn;
    @FXML
    private TableColumn<Collaborateur, String> siteColumn;
    @FXML
    private Text nameField;
    @FXML
    private Text firstNameField;
    @FXML
    private Text phoneField;
    @FXML
    private Text mobileField;
    @FXML
    private Text emailField;
    @FXML
    private Text serviceField;
    @FXML
    private Text siteField;

    private ObservableList<Collaborateur> collaborators = FXCollections.observableArrayList();
    private FilteredList<Collaborateur> filteredData;
    private List<Map<String, Object>> employees;

    @FXML
    public void initialize() {
        // Initialisation des comboboxes
        siteComboBox.setItems(FXCollections.observableArrayList("Paris", "Nantes", "Toulouse", "Nice", "Lille"));
        serviceComboBox.setItems(FXCollections.observableArrayList("Comptabilité", "Production", "Accueil", "Informatique", "Commercial"));

        // Configuration des colonnes de la TableView
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneFixe"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("telephonePortable"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));

        // Récupérer les collaborateurs depuis l'API
        fetchCollaborators();

        // Ajouter des écouteurs pour les filtres
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        siteComboBox.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        serviceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());

        // Ajouter un listener pour les éléments sélectionnés
        collaboratorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void fetchCollaborators() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/employees/listall"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> apiResponse = mapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
            Map<String, Object> data = (Map<String, Object>) apiResponse.get("data");
            employees = (List<Map<String, Object>>) data.get("body");

            for (Map<String, Object> employee : employees) {
                Integer id = employee.get("id") != null ? Integer.parseInt(employee.get("id").toString()) : null;
                String nom = employee.get("nom") != null ? employee.get("nom").toString() : "";
                String prenom = employee.get("prenom") != null ? employee.get("prenom").toString() : "";
                String telephoneFixe = employee.get("telephone_fixe") != null ? employee.get("telephone_fixe").toString() : "Data not available";
                String telephonePortable = employee.get("telephone_portable") != null ? employee.get("telephone_portable").toString() : "Data not available";
                System.out.println("Fixed phone: " + telephoneFixe + ", Mobile phone: " + telephonePortable);
                String email = employee.get("email") != null ? employee.get("email").toString() : "";
                String service = employee.get("service") != null ? employee.get("service").toString() : "";
                String site = employee.get("site") != null ? employee.get("site").toString() : "";

                System.out.println("Fetched employee with fixed phone: " + telephoneFixe + " and mobile phone: " + telephonePortable);

                Collaborateur collaborateur = new Collaborateur(id, nom, prenom, telephoneFixe, telephonePortable, email, service, site);
                collaborators.add(collaborateur);
            }

            filteredData = new FilteredList<>(collaborators, p -> true);
            collaboratorTableView.setItems(filteredData);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching collaborators: " + e.getMessage());
        }
    }

    private void applyFilters() {
        String searchText = searchField.getText().toLowerCase();
        String selectedSite = siteComboBox.getValue();
        String selectedService = serviceComboBox.getValue();

        filteredData.setPredicate(new Predicate<Collaborateur>() {
            @Override
            public boolean test(Collaborateur collaborator) {
                boolean matchesSearchText = searchText.isEmpty() || (collaborator.getNom().toLowerCase().contains(searchText) || collaborator.getPrenom().toLowerCase().contains(searchText));
                boolean matchesSite = selectedSite == null || collaborator.getSite().equals(selectedSite);
                boolean matchesService = selectedService == null || collaborator.getService().equals(selectedService);
                return matchesSearchText && matchesSite && matchesService;
            }
        });
    }

    private void showDetails(Collaborateur collaborator) {
        if (collaborator != null) {
            System.out.println("Affichage des détails: " + collaborator.getNom() + collaborator.getPrenom() + collaborator.getTelephoneFixe() + collaborator.getTelephonePortable());
            nameField.setText(collaborator.getNom());
            firstNameField.setText(collaborator.getPrenom());
            phoneField.setText(collaborator.getTelephoneFixe());
            mobileField.setText(collaborator.getTelephonePortable());
            emailField.setText(collaborator.getEmail());
            serviceField.setText(collaborator.getService());
            siteField.setText(collaborator.getSite());
        } else {
            nameField.setText("");
            firstNameField.setText("");
            phoneField.setText("");
            mobileField.setText("");
            emailField.setText("");
            serviceField.setText("");
            siteField.setText("");
        }
    }
}
