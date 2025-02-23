package tn.esprit.evenement.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.services.ServiceEvenement;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminEventsController implements Initializable {
    @FXML
    private ListView<Evenement> eventListView;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (eventListView == null) {
            System.out.println("ERREUR : eventListView est NULL !");
        } else {
            System.out.println("SUCCESS : eventListView est bien initialisée !");
        }
        loadEvents();
        eventListView.setCellFactory(param -> new EventListCell());
    }

    void loadEvents() {
        try {
            ObservableList<Evenement> events = FXCollections.observableArrayList(serviceEvenement.afficher());
            System.out.println("Nombre d'événements chargés : " + events.size());
            eventListView.setItems(events);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des événements: " + e.getMessage());
        }
    }

    private class EventListCell extends ListCell<Evenement> {
        @Override
        protected void updateItem(Evenement event, boolean empty) {
            super.updateItem(event, empty);
            if (empty || event == null) {
                setGraphic(null);
            } else {
                HBox container = new HBox(20); // Conteneur principal
                container.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-background-color: #f9f9f9; -fx-border-radius: 5; -fx-background-radius: 5;");

                // Création des labels pour afficher les informations de l'événement
                Label titleLabel = new Label("Titre: ");
                titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Label titleValue = new Label(event.getTitre());
                titleValue.setStyle("-fx-text-fill: black;");

                Label dateLabel = new Label("Date: ");
                dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Label dateValue = new Label(event.getDate().toString()); // Utilisation de Date

                Label heureLabel = new Label("Heure: ");
                heureLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Label heureValue = new Label(event.getHeure().toString()); // Utilisation de Time

                Label capacityLabel = new Label("Capacité: ");
                capacityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Label capacityValue = new Label(String.valueOf(event.getCapacite()));

                Label participantsLabel = new Label("Participants: ");
                participantsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Label participantsValue = new Label(String.valueOf(event.getNombreParticipants()));

                // Création des boutons d'actions
                Button modifyButton = createIconButton(FontAwesomeIcon.PENCIL, "#4CAF50");
                Button deleteButton = createIconButton(FontAwesomeIcon.TRASH, "#F44336");
                Button detailsButton = createIconButton(FontAwesomeIcon.EYE, "#2196F3");

                modifyButton.setOnAction(e -> handleModifyEvent(event));
                deleteButton.setOnAction(e -> handleDeleteEvent(event));
                detailsButton.setOnAction(e -> handleDetailsEvent(event));

                // Ajout des icônes dans un HBox pour qu'elles restent sur la même ligne
                HBox actionBox = new HBox(10, modifyButton, deleteButton, detailsButton);

                // Ajout des éléments dans le container principal
                container.getChildren().addAll(titleLabel, titleValue, dateLabel, dateValue, heureLabel, heureValue, capacityLabel, capacityValue, participantsLabel, participantsValue, actionBox);

                setGraphic(container);
            }
        }
    }

    private Button createIconButton(FontAwesomeIcon icon, String color) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setSize("14");
        iconView.setFill(Color.valueOf(color));

        Button button = new Button();
        button.setGraphic(iconView);
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }

    private void handleModifyEvent(Evenement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyEventView.fxml"));
            AnchorPane root = loader.load();

            ModifyEventsController controller = loader.getController();
            controller.setEventToModify(event);

            Scene scene = new Scene(root, 800, 600);
            Stage modifyStage = new Stage();
            modifyStage.setTitle("Modifier l'événement");
            modifyStage.setScene(scene);
            modifyStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteEvent(Evenement event) {
        try {
            serviceEvenement.supprimer(event.getId());
            loadEvents();
            showAlert("Succès", "Événement supprimé avec succès!");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }

    private void handleDetailsEvent(Evenement event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEventView.fxml"));
            AnchorPane root = loader.load();

            // Récupération du contrôleur et passage de l'événement
            DetailsEventsController controller = loader.getController();
            controller.setEventDetails(event);

            Scene scene = new Scene(root, 900, 600);
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Détails de l'événement");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleRefresh() {
        System.out.println("Actualisation de la liste des événements...");
        loadEvents();
    }

    @FXML
    private void handleAddEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEventView.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            Stage addStage = new Stage();
            addStage.setTitle("Ajouter un événement");
            addStage.setScene(scene);
            addStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
