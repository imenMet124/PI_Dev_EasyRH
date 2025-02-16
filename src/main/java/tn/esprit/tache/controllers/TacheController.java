package tn.esprit.tache.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.tache.entities.Tache;
import tn.esprit.tache.services.TacheService;
import javafx.collections.FXCollections;


import java.sql.Date;

public class TacheController {
    @FXML private TableView<Tache> tacheTable;
    @FXML private TableColumn<Tache, Integer> idCol;
    @FXML private TableColumn<Tache, String> titreCol;
    @FXML private TableColumn<Tache, String> statutCol;
    @FXML private TextField txtTitre;
    @FXML private TextField txtDescription;
    @FXML private ComboBox<String> cbPriorite;
    @FXML private DatePicker dpDeadline;
    @FXML private ComboBox<String> cbStatut;
    @FXML private ComboBox<String> cbAssignedTo;

    private final TacheService tacheService = new TacheService();

    @FXML
    public void initialize() {
        cbPriorite.setItems(FXCollections.observableArrayList("Haute", "Moyenne", "Basse"));
        cbStatut.setItems(FXCollections.observableArrayList("En attente", "En cours", "Terminée"));

        loadEmployeeNames();
        loadTasks();

        idCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(cellData.getValue()::getId));
        titreCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(cellData.getValue()::getTitre));
        statutCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(cellData.getValue()::getStatut));
    }

    private void loadTasks() {
        ObservableList<Tache> taches = tacheService.getAllTaches();
        tacheTable.setItems(taches);
    }

    private void loadEmployeeNames() {
        cbAssignedTo.setItems(FXCollections.observableArrayList(tacheService.getEmployeeNames()));
    }

    @FXML
    private void ajouterTache() {
        if (fieldsAreValid()) {
            Tache nouvelleTache = new Tache(
                    0, txtTitre.getText(), txtDescription.getText(),
                    cbPriorite.getValue(), Date.valueOf(dpDeadline.getValue()),
                    Integer.parseInt(cbAssignedTo.getValue().split(" - ")[0]),
                    cbStatut.getValue()
            );

            tacheService.addTache(nouvelleTache);
            loadTasks();
        }
    }

    @FXML
    private void modifierTache() {
        Tache selectedTache = tacheTable.getSelectionModel().getSelectedItem();
        if (selectedTache != null && fieldsAreValid()) {
            selectedTache.setTitre(txtTitre.getText());
            selectedTache.setDescription(txtDescription.getText());
            selectedTache.setPriorite(cbPriorite.getValue());
            selectedTache.setDeadline(Date.valueOf(dpDeadline.getValue()));
            selectedTache.setAssignedTo(Integer.parseInt(cbAssignedTo.getValue().split(" - ")[0]));
            selectedTache.setStatut(cbStatut.getValue());

            tacheService.updateTache(selectedTache);
            loadTasks();
        }
    }

    @FXML
    private void supprimerTache() {
        Tache selectedTache = tacheTable.getSelectionModel().getSelectedItem();
        if (selectedTache != null) {
            tacheService.deleteTache(selectedTache.getId());
            loadTasks();
        }
    }

    private boolean fieldsAreValid() {
        return !txtTitre.getText().isEmpty() &&
                !txtDescription.getText().isEmpty() &&
                cbPriorite.getValue() != null &&
                dpDeadline.getValue() != null &&
                cbStatut.getValue() != null &&
                cbAssignedTo.getValue() != null;
    }
}
