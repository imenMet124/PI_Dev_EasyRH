package tn.esprit.tache.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.tache.entities.Employe;
import tn.esprit.tache.services.EmployeService;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class EmployeController {

    @FXML private TableView<Employe> employeTable;
    @FXML private TableColumn<Employe, Integer> idCol;
    @FXML private TableColumn<Employe, String> nomCol;
    @FXML private TableColumn<Employe, String> roleCol;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer;
    @FXML private TextField txtNom;
    @FXML private TextField txtRole;

    private final EmployeService employeService = new EmployeService();
    private ObservableList<Employe> employeList;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nomCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        roleCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));

        chargerEmployes();
    }

    private void chargerEmployes() {
        try {
            List<Employe> employes = employeService.afficher();
            employeList = FXCollections.observableArrayList(employes);
            employeTable.setItems(employeList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer les employés !");
        }
    }

    @FXML
    private void ajouterEmploye() {
        String nom = txtNom.getText();
        String role = txtRole.getText();

        if (nom.isEmpty() || role.isEmpty()) {
            showAlert("Attention", "Veuillez remplir tous les champs !");
            return;
        }

        Employe nouvelEmploye = new Employe(0, nom, "email@example.com", role, "Position", new Date(), 1);
        try {
            employeService.ajouter(nouvelEmploye);
            chargerEmployes();
            txtNom.clear();
            txtRole.clear();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter l'employé !");
        }
    }

    @FXML
    private void modifierEmploye() {
        Employe selected = employeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un employé !");
            return;
        }

        String newRole = txtRole.getText();
        if (newRole.isEmpty()) {
            showAlert("Attention", "Veuillez entrer un nouveau rôle !");
            return;
        }

        try {
            selected.setRole(newRole);
            employeService.modifier(selected);
            chargerEmployes();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de modifier l'employé !");
        }
    }

    @FXML
    private void supprimerEmploye() {
        Employe selected = employeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un employé !");
            return;
        }

        try {
            employeService.supprimer(selected.getId());
            employeList.remove(selected);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de supprimer l'employé !");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
