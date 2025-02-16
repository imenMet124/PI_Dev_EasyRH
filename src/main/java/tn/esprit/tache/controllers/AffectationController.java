package tn.esprit.tache.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.tache.entities.Affectation;
import tn.esprit.tache.services.AffectationService;
import java.sql.SQLException;
import java.util.List;

public class AffectationController {

    @FXML private TableView<Affectation> affectationTable;
    @FXML private TableColumn<Affectation, Integer> idCol;
    @FXML private TableColumn<Affectation, Integer> tacheCol;
    @FXML private TableColumn<Affectation, Integer> employeCol;
    @FXML private TableColumn<Affectation, String> dateAffectationCol;
    @FXML private Button btnAjouter, btnSupprimer;

    private final AffectationService affectationService = new AffectationService();
    private ObservableList<Affectation> affectationList;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        tacheCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdTache()));
        employeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdEmploye()));
        dateAffectationCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateAffectation().toString()));

        chargerAffectations();
    }

    private void chargerAffectations() {
        try {
            List<Affectation> affectations = affectationService.afficher();
            affectationList = FXCollections.observableArrayList(affectations);
            affectationTable.setItems(affectationList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer les affectations !");
        }
    }

    @FXML
    private void ajouterAffectation() {
        // Code pour ajouter une affectation
    }

    @FXML
    private void supprimerAffectation() {
        Affectation selected = affectationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                affectationService.supprimer(selected.getId());
                affectationList.remove(selected);
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de supprimer l'affectation !");
            }
        } else {
            showAlert("Attention", "Veuillez sélectionner une affectation !");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
