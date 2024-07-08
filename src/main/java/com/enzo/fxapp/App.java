package com.enzo.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class App extends Application {

    private boolean kPressed = false;
    private boolean pPressed = false;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 900);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.K) {
                kPressed = true;
            } else if (event.getCode() == KeyCode.P) {
                pPressed = true;
            }

            if (kPressed && pPressed) {
                openAdminPage();
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.K) {
                kPressed = false;
            } else if (event.getCode() == KeyCode.P) {
                pPressed = false;
            }
        });

        stage.setScene(scene);
        stage.setTitle("EnzoApp");
        stage.setResizable(false);
        stage.show();
    }

    private void openAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Admin.fxml"));
            Scene adminScene = new Scene(loader.load());
            Stage adminStage = new Stage();
            adminStage.setTitle("Administration");
            adminStage.initModality(Modality.APPLICATION_MODAL);
            adminStage.setScene(adminScene);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
