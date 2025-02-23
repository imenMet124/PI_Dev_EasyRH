package tn.esprit.evenement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

public class AddEventController {

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private TextField heureField;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private ImageView eventImageView;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private String imagePath = null;

    // ✅ Gérer l'ajout d'un événement
    @FXML
    private void handleAjouterEvent() {
        if (!validateFields()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // ✅ Création de l'objet Evenement
        Evenement newEvent = new Evenement(
                titreField.getText(),
                descriptionField.getText(),
                Date.valueOf(datePicker.getValue()),
                Time.valueOf(heureField.getText()),
                lieuField.getText(),
                Integer.parseInt(capaciteField.getText()),
                imagePath
        );

        try {
            serviceEvenement.ajouter(newEvent);
            showAlert("Succès", "Événement ajouté avec succès !");
            closeWindow();
        } catch (SQLException e) {
            showAlert("Erreur", "Problème lors de l'ajout : " + e.getMessage());
        }
    }

    // ✅ Vérifier les champs obligatoires
    private boolean validateFields() {
        return !titreField.getText().isEmpty() &&
                !descriptionField.getText().isEmpty() &&
                datePicker.getValue() != null &&
                !heureField.getText().isEmpty() &&
                !lieuField.getText().isEmpty() &&
                !capaciteField.getText().isEmpty();
    }

    // ✅ Gérer l'upload d'image
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
                imagePath = destFile.getAbsolutePath();

            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload : " + e.getMessage());
            }
        }
    }

    // ✅ Annuler l'ajout
    @FXML
    private void handleCancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annuler l'ajout");
        alert.setHeaderText("Voulez-vous vraiment annuler ?");
        alert.setContentText("Les informations non enregistrées seront perdues.");

        ButtonType boutonOui = new ButtonType("Oui");
        ButtonType boutonNon = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(boutonOui, boutonNon);

        alert.showAndWait().ifPresent(response -> {
            if (response == boutonOui) {
                closeWindow();
            }
        });
    }

    // ✅ Fermer la fenêtre
    private void closeWindow() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    // ✅ Afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
