package tn.esprit.evenement.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.evenement.entities.Evenement;
import tn.esprit.evenement.entities.Participation;
import tn.esprit.evenement.services.ServiceEvenement;
import tn.esprit.evenement.services.ServiceParticipation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserEventsController implements Initializable {

    @FXML private TableView<Evenement> eventTable;
    @FXML private TableColumn<Evenement, Integer> idCol;
    @FXML private TableColumn<Evenement, String> titreCol;
    @FXML private TableColumn<Evenement, String> descriptionCol;
    @FXML private TableColumn<Evenement, String> dateCol;
    @FXML private TableColumn<Evenement, String> lieuCol;
    @FXML private TableColumn<Evenement, Integer> capaciteCol;
    @FXML private TableColumn<Evenement, Integer> participantsCol;

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private TextField nombreParticipationField;


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
    }

    private void initializeColumns() {

       // idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
      //  descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
      //  lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        // participantsCol.setCellValueFactory(new PropertyValueFactory<>("nombreParticipants"));// Ajouter la colonne nombreParticipants
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
    private void showEventDetails(Evenement event) {
        titreField.setText(event.getTitre());
        descriptionField.setText(event.getDescription());

//        // Vérifiez si l'événement a une date
//        if (event.getDate() != null) {
//            // Conversion de java.sql.Date en LocalDate
//            long localDate = event.getDate().toEpochDay();
//            datePicker.setValue(LocalDate.ofEpochDay(localDate));
//        }

      //  datePicker.setValue(event.getDate().toLocalDateTime().toLocalDate());
        lieuField.setText(event.getLieu());
        capaciteField.setText(String.valueOf(event.getCapacite()));
        nombreParticipationField.setText(String.valueOf(event.getCapacite()));


    }

    @FXML
    private void handleInscription() throws SQLException {
        Evenement selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            // ID de l'utilisateur (à remplacer par l'ID réel de l'utilisateur connecté)
            int currentUserId = 1;
            LocalDate currentDate = LocalDate.now();

            // Créer une nouvelle participation
            Participation newParticipation = new Participation(selectedEvent.getId(), currentUserId, currentDate, "En attente");

            // Ajouter la participation à la base de données
            serviceParticipation.ajouterParticipation(newParticipation);

            // Incrémenter le nombre de participants de l'événement
            serviceEvenement.incrementerParticipants(selectedEvent.getId());

            showAlert("Succès", "Vous êtes inscrit à l'événement: " + selectedEvent.getTitre());
        } else {
            showAlert("Erreur", "Veuillez sélectionner un événement pour vous inscrire");
        }
    }

    @FXML
    private void handleRefresh() {
        loadEvents();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
