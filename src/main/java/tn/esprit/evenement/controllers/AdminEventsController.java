package tn.esprit.evenement.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.services.ServiceEvenement;
import tn.esprit.evenement.services.ServiceParticipation;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class AdminEventsController implements Initializable {
    @FXML private TableView<Evenement> eventTable;
    @FXML private TableColumn<Evenement, Integer> idCol;
    @FXML private TableColumn<Evenement, String> titreCol;
    @FXML private TableColumn<Evenement, String> descriptionCol;
    @FXML private TableColumn<Evenement, Timestamp> dateCol;
    @FXML private TableColumn<Evenement, String> lieuCol;
    @FXML private TableColumn<Evenement, Integer> capaciteCol;
    @FXML private TableColumn<Evenement, Integer> participantsCol;

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private TextField nombreParticipationField;
    // @FXML private Button participantsButton;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceParticipation serviceParticipation = new ServiceParticipation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadEvents();

        eventTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        showEventDetails(newSelection);
                    }
                });

        // detailsButton.setOnAction(e -> handleAfficherDetails());
        // participantsButton.setOnAction(e -> handleAfficherParticipants()); // Décommenter lorsque cette fonctionnalité est prête
    }

    private void initializeColumns() {
        //   idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        // descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        // lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        //  participantsCol.setCellValueFactory(new PropertyValueFactory<>("participants"));
        participantsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNombreParticipants()).asObject());

    }

    private void loadEvents() {
        try {
            ObservableList<Evenement> events = FXCollections.observableArrayList(serviceEvenement.afficher());
            eventTable.setItems(events);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des événements: " + e.getMessage());
        }
    }

    @FXML
    private void handleAjouterEvent() {
        if (validateFields()) {
            try {
                Evenement newEvent = getEventFromFields();
                serviceEvenement.ajouter(newEvent);
                loadEvents();
                clearFields();
                showAlert("Succès", "Événement ajouté avec succès!");
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de l'ajout: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleModifierEvent() {
        Evenement selected = eventTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement à modifier");
            return;
        }

        if (validateFields()) {
            try {
                Evenement updated = getEventFromFields();
                updated.setId(selected.getId());
                serviceEvenement.modifier(updated);
                loadEvents();
                showAlert("Succès", "Événement modifié avec succès!");
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la modification: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSupprimerEvent() {
        Evenement selected = eventTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement à supprimer");
            return;
        }

        try {
            serviceEvenement.supprimer(selected.getId());
            loadEvents();
            clearFields();
            showAlert("Succès", "Événement supprimé avec succès!");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadEvents();
    }

    private Evenement getEventFromFields() {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            showAlert("Erreur", "Veuillez sélectionner une date valide.");
            return null;
        }
        return new Evenement(
                titreField.getText(),
                descriptionField.getText(),
                Timestamp.valueOf(date.atStartOfDay()),
                lieuField.getText(),
                Integer.parseInt(capaciteField.getText())
        );
    }

    private void showEventDetails(Evenement event) {
        titreField.setText(event.getTitre());
        descriptionField.setText(event.getDescription());

//        // Vérifiez si l'événement a une date
//        if (event.getDate() != null) {
//            // Conversion de java.sql.Date en LocalDate
//            long localDate = event.getDate().toEpochDay();
//            datePicker.setValue(LocalDate.ofEpochDay(localDate));
//        }

        datePicker.setValue(event.getDate().toLocalDateTime().toLocalDate());
        lieuField.setText(event.getLieu());
        capaciteField.setText(String.valueOf(event.getCapacite()));

    }

    private void clearFields() {
        titreField.clear();
        descriptionField.clear();
        datePicker.setValue(null);
        lieuField.clear();
        capaciteField.clear();
    }

    private boolean validateFields() {
        if (titreField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                datePicker.getValue() == null || lieuField.getText().isEmpty() || capaciteField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs");
            return false;
        }
        try {
            Integer.parseInt(capaciteField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La capacité doit être un nombre valide");
            return false;
        }
        return true;
    }

    @FXML
    private void handleAfficherDetails() {
        Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            titreField.setText(selectedEvent.getTitre());
            descriptionField.setText(selectedEvent.getDescription());

//            // Vérifiez si l'événement a une date
//            if (selectedEvent.getDate() != null) {
//                // Conversion de java.sql.Date en LocalDate
//                long localDate = selectedEvent.getDate().toEpochDay();
//                datePicker.setValue(LocalDate.ofEpochDay(localDate));
//            }

            datePicker.setValue(selectedEvent.getDate().toLocalDateTime().toLocalDate());
            lieuField.setText(selectedEvent.getLieu());
            capaciteField.setText(String.valueOf(selectedEvent.getCapacite()));
        } else {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement pour voir ses détails.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}