package tn.esprit.tache.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private ImageView logoImage;

    @FXML
    public void initialize() {
        // Charger l'image correctement
        Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
        logoImage.setImage(logo);
    }

    @FXML
    private void afficherTaches() {
        chargerVue("/views/TacheView.fxml");
    }

    @FXML
    private void afficherProjets() {
        chargerVue("/views/ProjetView.fxml");
    }

    @FXML
    private void afficherEmployes() {
        chargerVue("/views/EmployeView.fxml");
    }

    @FXML
    private void afficherAffectations() {
        chargerVue("/views/AffectationView.fxml");
    }

    @FXML
    private void afficherCommentaires() {
        chargerVue("/views/CommentaireView.fxml");
    }

    private void chargerVue(String cheminFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cheminFXML));
            Stage stage = (Stage) logoImage.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement de la vue : " + cheminFXML);
            e.printStackTrace();
        }
    }


}
