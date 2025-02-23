package tn.esprit.evenement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.services.ServiceEvenement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ModifyEventsController {

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private TextField heureField;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private TextField participantsField;
    @FXML private ImageView eventImageView;

    private Evenement eventToModify;
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    public void setEventToModify(Evenement event) {
        this.eventToModify = event;

        titreField.setText(event.getTitre());
        descriptionField.setText(event.getDescription());
        datePicker.setValue(event.getDate().toLocalDate());
        heureField.setText(event.getHeure().toString());
        lieuField.setText(event.getLieu());
        capaciteField.setText(String.valueOf(event.getCapacite()));
        participantsField.setText(String.valueOf(event.getNombreParticipants()));

        // ✅ Vérification de l’image
        if (event.getImagePath() != null && !event.getImagePath().isEmpty()) {
            File file = new File(event.getImagePath());

            if (file.exists()) {
                eventImageView.setImage(new Image(file.toURI().toString()));
            } else {
                System.out.println("⚠️ L’image spécifiée n’existe pas !");
                eventImageView.setImage(new Image("/images/default.png")); // Image par défaut
            }
        } else {
            System.out.println("⚠️ Aucune image spécifiée, utilisation de l’image par défaut.");
            eventImageView.setImage(new Image("/images/default.png")); // Image par défaut si NULL
        }
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                File destDir = new File("C:/images/events/");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destFile = new File(destDir, newFileName);

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                eventImageView.setImage(new Image(destFile.toURI().toString()));
            } catch (IOException e) {
                System.out.println("⚠️ Erreur lors de l'upload : " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleModifierEvent() {
        if (eventToModify == null) {
            showAlert("Erreur", "Aucun événement sélectionné.");
            return;
        }

        eventToModify.setTitre(titreField.getText());
        eventToModify.setDescription(descriptionField.getText());
        eventToModify.setDate(Date.valueOf(datePicker.getValue()));
        eventToModify.setHeure(Time.valueOf(heureField.getText()));
        eventToModify.setLieu(lieuField.getText());
        eventToModify.setCapacite(Integer.parseInt(capaciteField.getText()));

        try {
            serviceEvenement.modifier(eventToModify);
            showAlert("Succès", "Événement modifié avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur", "Problème lors de la modification : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler la modification");
        alert.setHeaderText("Voulez-vous vraiment annuler ?");
        alert.setContentText("Les modifications non enregistrées seront perdues.");

        ButtonType boutonOui = new ButtonType("Oui");
        ButtonType boutonNon = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(boutonOui, boutonNon);

        alert.showAndWait().ifPresent(response -> {
            if (response == boutonOui) {
                closeWindow();
            }
        });
    }

    private void closeWindow() {
        titreField.getScene().getWindow().hide(); // Fermer la fenêtre actuelle
    }

}
