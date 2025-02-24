package tn.esprit.evenement.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.entities.Participation;
import tn.esprit.evenement.entities.Utilisateur;
import tn.esprit.evenement.services.ServiceEvenement;
import tn.esprit.evenement.services.ServiceParticipation;
import tn.esprit.evenement.services.ServiceUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserEventsController implements Initializable {

    @FXML private ListView<Evenement> eventListView;
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceParticipation serviceParticipation = new ServiceParticipation();
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    private Utilisateur currentUser; // Utilisateur connecté (Remplacez par la session actuelle)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEventList();
        currentUser = serviceUtilisateur.getUtilisateurParId(1); // Simule un utilisateur connecté
    }

    private void loadEventList() {
        try {
            ObservableList<Evenement> events = FXCollections.observableArrayList(serviceEvenement.afficher());
            eventListView.setItems(events);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des événements: " + e.getMessage());
        }
    }


    @FXML
    private void handleInscription() {
        Evenement selectedEvent = eventListView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            LocalDate currentDate = LocalDate.now();

            try {
                if (selectedEvent.getNombreParticipants() >= selectedEvent.getCapacite()) {
                    showAlert("Erreur", "Cet événement est complet. Impossible de s'inscrire.");
                    return;
                }

                boolean alreadyRegistered = serviceParticipation.getParticipationsParUtilisateur(currentUser.getId())
                        .stream().anyMatch(p -> p.getEvenement().getId() == selectedEvent.getId());

                if (alreadyRegistered) {
                    showAlert("Erreur", "Vous êtes déjà inscrit à cet événement.");
                    return;
                }

                Participation newParticipation = new Participation(selectedEvent, currentUser, currentDate, "En attente");

                serviceParticipation.ajouterParticipation(newParticipation);

                serviceEvenement.incrementerParticipants(selectedEvent.getId());

                showAlert("Succès", "Vous êtes inscrit à l'événement: " + selectedEvent.getTitre());
                loadEventList(); // Rafraîchir la liste
            } catch (SQLException e) {
                showAlert("Erreur", "Problème lors de l'inscription : " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un événement pour vous inscrire.");
        }
    }


    @FXML
    private void handleDetailsEvent() {
        Evenement selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEventView.fxml"));
                AnchorPane root = loader.load();

                DetailsEventsController controller = loader.getController();
                controller.setEventDetails(selectedEvent);

                Stage stage = new Stage();
                stage.setTitle("Détails de l'événement");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'afficher les détails : " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un événement.");
        }
    }


    @FXML
    private void handleRefresh() {
        loadEventList();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
