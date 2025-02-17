package tn.esprit.Offres.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import tn.esprit.Offres.entities.Offre;
import tn.esprit.Offres.services.ServiceOffres;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjoutController {

    @FXML
    private TextField date;

    @FXML
    private TextField rec;

    @FXML
    private TextField stat;

    @FXML
    private TextField titre;

    @FXML
    private Button ajoutButton;

    @FXML
    private TextField dep;

    @FXML
    private TextArea desc;

    private final ServiceOffres serviceOffres = new ServiceOffres();

    @FXML
    private void initialize() {
        ajoutButton.setOnAction(event -> ajouterOffre());
    }

    @FXML
    public void ajouterOffre() {
        try {
            Offre offre = new Offre();
            offre.setTitrePoste(titre.getText());
            offre.setDescription(desc.getText());
            offre.setStatuOffre(stat.getText());
            offre.setDepartement(dep.getText());
            offre.setRecruteurResponsable(rec.getText());

            try {
                Date datePublication = Date.valueOf(date.getText());
                offre.setDatePublication(datePublication);
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.WARNING, "Format incorrect", "Veuillez entrer une date valide (YYYY-MM-DD).");
                return;
            }

            if (offre.getTitrePoste() == null || offre.getTitrePoste().trim().isEmpty() ||
                    offre.getDescription() == null || offre.getDescription().trim().isEmpty() ||
                    offre.getStatuOffre() == null || offre.getStatuOffre().trim().isEmpty() ||
                    offre.getDepartement() == null || offre.getDepartement().trim().isEmpty() ||
                    offre.getRecruteurResponsable() == null || offre.getRecruteurResponsable().trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            serviceOffres.ajouterModel(offre);
            System.out.println("Offre ajoutée avec succès !");

            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'offre a été ajoutée avec succès !");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Offres/views/NouvelleVue.fxml"));
            Parent root = loader.load();
            ViewController nouvelleVueController = loader.getController();
            nouvelleVueController.initialize();

            Scene currentScene = ajoutButton.getScene();
            currentScene.setRoot(root);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Une erreur est survenue lors de l'ajout de l'offre : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Une erreur est survenue lors du chargement de la vue : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}