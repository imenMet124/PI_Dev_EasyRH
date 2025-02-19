package tn.esprit.evenement.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.entities.Participation;
import tn.esprit.evenement.services.ServiceEvenement;
import tn.esprit.evenement.services.ServiceParticipation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ModifyEventsController {

    @FXML
    private TextField titreField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField lieuField;

    @FXML
    private TextField capaciteField;
    @FXML private TableView<Evenement> eventTable;

    private Evenement eventToModify; // Événement à modifier
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceParticipation serviceParticipation = new ServiceParticipation();


    private AdminEventsController adminEventsController; // Référence à AdminEventsController



    // Méthode pour initialiser l'événement à modifier
    public void setEventToModify(Evenement event) {
        this.eventToModify = event;
        // Afficher les détails de l'événement dans les champs
        titreField.setText(event.getTitre());
        descriptionField.setText(event.getDescription());
        datePicker.setValue(event.getDate().toLocalDateTime().toLocalDate());
        lieuField.setText(event.getLieu());
        capaciteField.setText(String.valueOf(event.getCapacite()));
    }

    @FXML
    private void handleModifierEvent() {
        if (eventToModify == null) {
            showAlert("Erreur", "Aucun événement à modifier.");
            return;
        }

        if (validateFields()) {
            try {
                Evenement updated = getEventFromFields();
                updated.setId(eventToModify.getId()); // Conserver l'ID de l'événement
                serviceEvenement.modifier(updated);

                // Rafraîchir la liste des événements dans AdminEventsController
                if (adminEventsController != null) {
                    adminEventsController.loadEvents();
                }

                showAlert("Succès", "Événement modifié avec succès!");
                closeWindow(); // Fermer la fenêtre après la modification
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la modification: " + e.getMessage());
            }
        }
    }
    private boolean validateFields() {
        if (titreField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                datePicker.getValue() == null || lieuField.getText().isEmpty() ||
                capaciteField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs");
            return false;
        }

        try {
            Integer.parseInt(capaciteField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La capacité doit être un nombre entier");
            return false;
        }

        return true;
    }

    private Evenement getEventFromFields() {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            showAlert("Erreur", "Veuillez sélectionner une date valide.");
            return null;
        }
        return new Evenement(
                titreField.getText(),
                descriptionField.getText(),
                Timestamp.valueOf(date.atStartOfDay()),
                lieuField.getText(),
                Integer.parseInt(capaciteField.getText())
        );
    }
    @FXML
    private void handleAjouterEvent() {
        if (validateFields()) {
            try {
                Evenement newEvent = getEventFromFields();
                serviceEvenement.ajouter(newEvent);
               // adminEventsController.loadEvents();
                //adminEventsController.clearFields();
                showAlert("Succès", "Événement ajouté avec succès!");
                closeWindow();
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de l'ajout: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        titreField.getScene().getWindow().hide(); // Fermer la fenêtre actuelle
    }
    @FXML
    private void handleInscription() throws SQLException {
        Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            int currentUserId = 1;
            LocalDate currentDate = LocalDate.now();
            Participation newParticipation = new Participation(selectedEvent.getId(), currentUserId, currentDate, "En attente");
            serviceParticipation.ajouterParticipation(newParticipation);
            serviceEvenement.incrementerParticipants(selectedEvent.getId());

            showAlert("Succès", "Vous êtes inscrit à l'événement: " + selectedEvent.getTitre());
        } else {
            showAlert("Erreur", "Veuillez sélectionner un événement pour vous inscrire");
        }
    }

}