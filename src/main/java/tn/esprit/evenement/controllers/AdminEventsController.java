package tn.esprit.evenement.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
    @FXML private TableColumn<Evenement, Void> actionCol;

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
// Configurer la colonne "Action"
        actionCol.setCellFactory(column -> new TableCell<Evenement, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final Button detailsButton = new Button("Details");

            {
                // Style des boutons
                modifyButton.getStyleClass().add("action-button");
                deleteButton.getStyleClass().add("action-button");
                detailsButton.getStyleClass().add("action-button");


                // Actions des boutons
                modifyButton.setOnAction(event -> {
                    Evenement eventToModify = getTableView().getItems().get(getIndex());
                    handleModifyEvent(eventToModify);
                });

                deleteButton.setOnAction(event -> {
                    Evenement eventToDelete = getTableView().getItems().get(getIndex());
                    handleDeleteEvent(eventToDelete);
                });
                detailsButton.setOnAction(event -> {
                    Evenement eventToDetail = getTableView().getItems().get(getIndex());
                    handleDetailsEvent(eventToDetail);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, modifyButton, deleteButton,detailsButton));
                }
            }
        });
    }

    void loadEvents() {
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

    void clearFields() {
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

    private void handleModifyEvent(Evenement event) {
        try {
            // Chargement du fichier FXML pour l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyEventView.fxml"));
            AnchorPane root = loader.load();

            // Récupérer le contrôleur
            ModifyEventsController controller = loader.getController();
            controller.setEventToModify(event); // Passer l'événement à modifier

            // Création de la scène
            Scene scene = new Scene(root, 800, 600);

            // Création d'une nouvelle fenêtre
            Stage modifyStage = new Stage();
            modifyStage.setTitle("Modifier l'événement");
            modifyStage.setScene(scene);

            // Affichage de la fenêtre
            modifyStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddEvent() {
        try {
            // Chargement du fichier FXML pour l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEventView.fxml"));
            AnchorPane root = loader.load();

            // Récupérer le contrôleur
            ModifyEventsController controller = loader.getController();

            // Création de la scène
            Scene scene = new Scene(root, 800, 600);

            // Création d'une nouvelle fenêtre
            Stage modifyStage = new Stage();
            modifyStage.setTitle("Modifier l'événement");
            modifyStage.setScene(scene);

            // Affichage de la fenêtre
            modifyStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteEvent(Evenement event) {

        try {
            serviceEvenement.supprimer(event.getId());
            loadEvents();
            showAlert("Succès", "Événement supprimé avec succès!");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }
    private void handleDetailsEvent(Evenement event) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEventView.fxml"));
            AnchorPane root = loader.load();

            // Récupérer les éléments de l'interface utilisateur
            TextField titreField = (TextField) root.lookup("#titreField");
            TextArea descriptionField = (TextArea) root.lookup("#descriptionField");
            DatePicker datePicker = (DatePicker) root.lookup("#datePicker");
            TextField lieuField = (TextField) root.lookup("#lieuField");
            TextField capaciteField = (TextField) root.lookup("#capaciteField");

            // Mettre à jour les champs avec les détails de l'événement
            titreField.setText(event.getTitre());
            descriptionField.setText(event.getDescription());
            datePicker.setValue(event.getDate().toLocalDateTime().toLocalDate());
            lieuField.setText(event.getLieu());
            capaciteField.setText(String.valueOf(event.getCapacite()));

            // Création de la scène
            Scene scene = new Scene(root, 800, 600);

            // Création d'une nouvelle fenêtre
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Détails de l'événement");
            detailsStage.setScene(scene);

            // Affichage de la fenêtre
            detailsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}