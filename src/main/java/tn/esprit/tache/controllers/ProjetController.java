package tn.esprit.tache.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.tache.entities.Projet;
import tn.esprit.tache.services.ProjetService;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert.AlertType;

public class ProjetController {

    @FXML private TableView<Projet> projetTable;
    @FXML private TableColumn<Projet, Integer> idCol;
    @FXML private TableColumn<Projet, String> nomCol;
    @FXML private TableColumn<Projet, String> statutCol;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer;

    @FXML private TextField txtNomProjet;
    @FXML private TextField txtStatutProjet;

    private final ProjetService projetService = new ProjetService();
    private ObservableList<Projet> projetList;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nomCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        statutCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatut()));

        chargerProjets();
    }

    private void chargerProjets() {
        try {
            List<Projet> projets = projetService.afficher();
            projetList = FXCollections.observableArrayList(projets);
            projetTable.setItems(projetList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer les projets !");
        }
    }

    @FXML
    private void ajouterProjet() {
        String nom = txtNomProjet.getText();
        String statut = txtStatutProjet.getText();

        if (nom.isEmpty() || statut.isEmpty()) {
            showAlert("Attention", "Veuillez remplir tous les champs !");
            return;
        }

        Projet nouveauProjet = new Projet(0, nom, statut, null, null, null);
        try {
            projetService.ajouter(nouveauProjet);
            chargerProjets();
            txtNomProjet.clear();
            txtStatutProjet.clear();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le projet !");
        }
    }

    @FXML
    private void modifierProjet() {
        Projet selected = projetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un projet !");
            return;
        }

        String newStatut = txtStatutProjet.getText();
        if (newStatut.isEmpty()) {
            showAlert("Attention", "Veuillez entrer un nouveau statut !");
            return;
        }

        try {
            selected.setStatut(newStatut);
            projetService.modifier(selected);
            chargerProjets();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de modifier le projet !");
        }
    }

    @FXML
    private void supprimerProjet() {
        Projet selected = projetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un projet !");
            return;
        }

        try {
            projetService.supprimer(selected.getId());
            projetList.remove(selected);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de supprimer le projet !");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
