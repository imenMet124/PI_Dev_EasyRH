package tn.esprit.evenement.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage extends Application {
    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        try {
            if (logoImage != null) {
                // Charger l'image depuis le dossier resources/images/
                Image image = new Image(getClass().getResource("/logo.jpg").toExternalForm());
                logoImage.setImage(image);
            } else {
                System.out.println("ERREUR: `logoImage` est NULL ! Vérifie le `fx:id` dans le FXML.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du logo : " + e.getMessage());
        }
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IndexView.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root, 800, 600);
          //  scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            primaryStage.setTitle("Liste des événements");
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleListEvent(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminEventsView.fxml"));
            AnchorPane root = loader.load();

//// Vérification que le contrôleur est bien initialisé
//            AdminEventsController controller = loader.getController();
//            if (controller == null) {
//                System.out.println("ERREUR: Le contrôleur AdminEventsController n'a pas été chargé !");
//            } else {
//                System.out.println("SUCCESS: Le contrôleur AdminEventsController est bien initialisé.");
//            }

            Scene scene = new Scene(root, 800, 600);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
