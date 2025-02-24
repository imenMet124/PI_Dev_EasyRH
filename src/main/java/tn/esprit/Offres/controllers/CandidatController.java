package tn.esprit.Offres.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.entities.User;
import tn.esprit.Offres.services.ServiceCandidat;
import tn.esprit.Offres.services.ServiceOffres;
import tn.esprit.Offres.services.ServiceUser;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CandidatController {

    @FXML
    private TextField emailC;

    @FXML
    private Button confirmerC;

    @FXML
    private TextField compC;

    @FXML
    private ComboBox<Disponibilite> dispC;

    @FXML
    private TextField nomC;

    @FXML
    private TextField expC;

    @FXML
    private TextField depC;

    @FXML
    private TextField telC;

    @FXML
    private TextField posteC;
    private final ServiceCandidat serviceCandidat = new ServiceCandidat();
    private final ServiceUser serviceUser = new ServiceUser();

    public enum Disponibilite {
        IMMEDIATE, UN_MOIS, DEUX_MOIS, TROIS_MOIS
    }



    @FXML
    public void initialize() {
        // Ajouter toutes les valeurs de l'Enum dans la ComboBox
        dispC.setItems(FXCollections.observableArrayList(Disponibilite.values()));
        dispC.getSelectionModel().select(0); // Sélectionne la première valeur par défaut
    }
    public void setNomC(String nom) {
        this.nomC.setText(nom);
    }
    User userConnecte = new User(
            1, // idEmp
            "Dupont",
            "dupont@example.com",
            "12345678",
            "Employé",
            "Développeur",
            3000.0,
            new java.util.Date(),
            "Actif",
            "Informatique"
    );
    public void remplirInformationsCandidat(User user) {
        nomC.setText(user.getNomEmp());
        emailC.setText(user.getEmail());
        depC.setText(user.getDepartment());
        telC.setText(user.getPhone());
        posteC.setText(user.getPosition());
        // Rendre les champs non éditables pour éviter la modification

    }
    @FXML
    private void onAjouterCandidatClick() {
        try {
            Candidat candidat = new Candidat();
            candidat.setUser(userConnecte);
            candidat.setNom(nomC.getText()); // Nom du candidat
            candidat.setEmail(emailC.getText()); // Email du candidat
            candidat.setPhone(telC.getText()); // Téléphone du candidat
            candidat.setPosition(posteC.getText()); // Poste du candidat
            candidat.setDepartment(depC.getText()); // Département du candidat
            candidat.setExperienceInterne(expC.getText());
            candidat.setCompetence(compC.getText());
            candidat.setDisponibilite(tn.esprit.Offres.entities.Candidat.Disponibilite.valueOf(dispC.getValue().name()));
            if (userConnecte == null) {
                System.out.println("Erreur : Aucun utilisateur connecté !");
                return;
            }
            // Ajout du candidat
            serviceCandidat.ajouter(candidat);

            System.out.println("Candidat ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

}
