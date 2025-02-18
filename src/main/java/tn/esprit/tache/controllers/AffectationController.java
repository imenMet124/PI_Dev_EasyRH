package tn.esprit.tache.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.tache.entities.Affectation;
import tn.esprit.tache.entities.Tache;
import tn.esprit.tache.services.AffectationService;
import tn.esprit.tache.services.EmployeService;
import tn.esprit.tache.services.TacheService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class AffectationController {

    @FXML private TableView<Affectation> affectationTable;
    @FXML private TableColumn<Affectation, Integer> idAffectationCol;
    @FXML private TableColumn<Affectation, String> employeCol;
    @FXML private TableColumn<Affectation, String> tacheCol;
    @FXML private TableColumn<Affectation, String> deadlineCol;
    @FXML private TableColumn<Affectation, Date> dateCol;
    @FXML private TableColumn<Affectation, String> statutCol; // ✅ Ajout de la colonne statut
    @FXML private Button ajouterBtn;
    @FXML private Button retirerBtn;
    @FXML private Button terminerTacheBtn;

    private final AffectationService affectationService = new AffectationService();
    private final EmployeService employeService = new EmployeService();
    private final TacheService tacheService = new TacheService();
    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        loadAffectations();

        idAffectationCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getIdAffectation())
        );

        employeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(employeService.getEmployeById(cellData.getValue().getIdEmp()).getNomEmp())
        );

        tacheCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(tacheService.getTacheById(cellData.getValue().getIdTache()).getTitreTache())
        );

        dateCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(new java.sql.Date(cellData.getValue().getDateAffectation().getTime()))
        );

        // ✅ Affichage formaté de la deadline
        deadlineCol.setCellValueFactory(cellData -> {
            Tache tache = tacheService.getTacheById(cellData.getValue().getIdTache());
            if (tache != null && tache.getDeadline() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return new SimpleObjectProperty<>(dateFormat.format(tache.getDeadline()));
            } else {
                return new SimpleObjectProperty<>("Pas de deadline");
            }
        });

        // ✅ Statut : "En cours" ou "Terminé" avec couleur
        statutCol.setCellValueFactory(cellData -> {
            Tache tache = tacheService.getTacheById(cellData.getValue().getIdTache());
            return new SimpleStringProperty(tache != null && "Terminé".equals(tache.getStatutTache()) ? "Terminé" : "En cours");
        });

        statutCol.setCellFactory(column -> new TableCell<Affectation, String>() {
            @Override
            protected void updateItem(String statut, boolean empty) {
                super.updateItem(statut, empty);
                if (empty || statut == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(statut);
                    if ("Terminé".equals(statut)) {
                        setStyle("-fx-background-color: #28A745; -fx-text-fill: white; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void loadAffectations() {
        List<Affectation> affectations = affectationService.getAllAffectations();
        ObservableList<Affectation> observableList = FXCollections.observableArrayList(affectations);
        affectationTable.setItems(observableList);
    }

    @FXML
    private void ouvrirFormulaireAffectation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/affectation_form.fxml"));
            Parent root = loader.load();

            AffectationFormController formController = loader.getController();
            formController.initData(affectationService, employeService, tacheService);

            Stage stage = new Stage();
            stage.setTitle("Ajouter Affectation");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadAffectations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retirerEmployeTache() {
        Affectation selectedAffectation = affectationTable.getSelectionModel().getSelectedItem();
        if (selectedAffectation == null) {
            showAlert("Erreur", "Veuillez sélectionner une affectation à retirer.");
            return;
        }

        affectationService.retirerEmployeTache(selectedAffectation.getIdEmp(), selectedAffectation.getIdTache());
        loadAffectations();
    }

    @FXML
    private void terminerTache() {
        Affectation selectedAffectation = affectationTable.getSelectionModel().getSelectedItem();
        if (selectedAffectation == null) {
            showAlert("Erreur", "Veuillez sélectionner une affectation.");
            return;
        }

        Tache selectedTache = tacheService.getTacheById(selectedAffectation.getIdTache());
        if (selectedTache == null) {
            showAlert("Erreur", "Tâche introuvable.");
            return;
        }

        // ✅ Vérifier si la tâche est déjà terminée
        if ("Terminé".equals(selectedTache.getStatutTache())) {
            showAlert("Information", "Cette tâche est déjà terminée.");
            return;
        }

        // ✅ Mettre à jour la tâche comme terminée
        tacheService.terminerTache(selectedTache.getIdTache());
        selectedTache.setStatutTache("Terminé");
        loadAffectations();  // ✅ Recharger les données après modification
        showAlert("Succès", "La tâche a été marquée comme terminée.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void rechercherAffectation() {
        String searchText = searchField.getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            loadAffectations();
            return;
        }

        List<Affectation> allAffectations = affectationService.getAllAffectations();
        ObservableList<Affectation> filteredList = FXCollections.observableArrayList();

        for (Affectation affectation : allAffectations) {
            String employeName = employeService.getEmployeById(affectation.getIdEmp()).getNomEmp().toLowerCase();
            String tacheTitle = tacheService.getTacheById(affectation.getIdTache()).getTitreTache().toLowerCase();

            if (employeName.contains(searchText) || tacheTitle.contains(searchText)) {
                filteredList.add(affectation);
            }
        }

        affectationTable.setItems(filteredList);
    }
}
