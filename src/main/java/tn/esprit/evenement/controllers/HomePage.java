package tn.esprit.evenement.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminEventsView.fxml"));
            AnchorPane root = loader.load();

            // Création de la scène et affectation de la racine
            Scene scene = new Scene(root, 800, 600);
          //  scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            primaryStage.setTitle("Liste des événements");
            primaryStage.setScene(scene);

            // Affichage de la fenêtre
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
