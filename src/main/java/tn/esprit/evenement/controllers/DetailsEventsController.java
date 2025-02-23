package tn.esprit.evenement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.evenement.entities.Evenement;

import java.io.File;

public class DetailsEventsController {

    @FXML private ImageView eventImageView;
    @FXML private Label titreLabel;
    @FXML private Label dateLabel;
    @FXML private Label heureLabel;
    @FXML private Label lieuLabel;
    @FXML private Label capaciteLabel;
    @FXML private Label participantsLabel;
    @FXML private TextArea descriptionArea;
    @FXML private Button closeButton;

    private Evenement evenement;

    public void setEventDetails(Evenement event) {
        this.evenement = event;

        titreLabel.setText(event.getTitre());
        dateLabel.setText(event.getDate().toString());
        heureLabel.setText(event.getHeure().toString());
        lieuLabel.setText(event.getLieu());
        capaciteLabel.setText(String.valueOf(event.getCapacite()));
        participantsLabel.setText(String.valueOf(event.getNombreParticipants()));
        descriptionArea.setText(event.getDescription());

        // Gestion de l'affichage de l'image
        if (event.getImagePath() != null && !event.getImagePath().isEmpty()) {
            File file = new File(event.getImagePath());
            if (file.exists()) {
                eventImageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
