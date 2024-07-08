package com.enzo.fxapp;

import com.enzo.fxapp.Controllers.LoginController;
import com.enzo.fxapp.Controllers.Client.MainLayoutController; // Import MainLayoutController
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage; // Le stage principal
    private boolean isAdmin = false; // État de connexion admin
    private boolean kPressed = false;
    private boolean pPressed = false;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage; // Initialiser la référence du stage
        openLoginPage(); // Ouvrir la page de login au démarrage
    }

    public void openLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - EnzoApp");
            primaryStage.show();

            LoginController loginController = loader.getController();
            loginController.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSuccessfulLogin(int role) {
        this.isAdmin = (role == 1); // Admin role is 1
        openMainPage(); // Ouvrir la page principale pour tous les utilisateurs
    }

    private void openMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
            Scene scene = new Scene(loader.load(), 1400, 900);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main Page - EnzoApp");

            // Set the app instance in the controller
            MainLayoutController mainLayoutController = loader.getController();
            mainLayoutController.setApp(this);

            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case K:
                        kPressed = true;
                        break;
                    case P:
                        pPressed = true;
                        break;
                }

                if (kPressed && pPressed && isAdmin) {
                    openAdminPage();
                }
            });

            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case K:
                        kPressed = false;
                        break;
                    case P:
                        pPressed = false;
                        break;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Admin.fxml"));
            Scene scene = new Scene(loader.load());
            Stage adminStage = new Stage();
            adminStage.setTitle("Administration Panel - EnzoApp");
            adminStage.setScene(scene);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
