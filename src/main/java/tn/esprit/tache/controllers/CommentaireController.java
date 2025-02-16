package tn.esprit.tache.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.tache.entities.Commentaire;
import tn.esprit.tache.services.CommentaireService;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class CommentaireController {

    @FXML private TableView<Commentaire> commentaireTable;
    @FXML private TableColumn<Commentaire, Integer> idCol;
    @FXML private TableColumn<Commentaire, String> contenuCol;
    @FXML private Button btnAjouter, btnSupprimer;
    @FXML private TextField txtCommentaire;

    private final CommentaireService commentaireService = new CommentaireService();
    private ObservableList<Commentaire> commentaireList;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        contenuCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContenu()));

        chargerCommentaires();
    }

    private void chargerCommentaires() {
        try {
            List<Commentaire> commentaires = commentaireService.afficher();
            commentaireList = FXCollections.observableArrayList(commentaires);
            commentaireTable.setItems(commentaireList);
            commentaireTable.refresh();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de récupérer les commentaires !");
            e.printStackTrace();
        }
    }

    @FXML
    private void ajouterCommentaire() {
        String contenu = txtCommentaire.getText();

        if (contenu.isEmpty()) {
            showAlert("Attention", "Veuillez entrer un commentaire !");
            return;
        }

        Commentaire nouveauCommentaire = new Commentaire(0, contenu, new Date(), 1, 1);
        try {
            commentaireService.ajouter(nouveauCommentaire);
            System.out.println("Commentaire ajouté avec succès !");
            chargerCommentaires();
            txtCommentaire.clear();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le commentaire !");
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerCommentaire() {
        Commentaire selected = commentaireTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un commentaire !");
            return;
        }

        try {
            commentaireService.supprimer(selected.getId());
            System.out.println("Commentaire supprimé: " + selected.getId());
            commentaireList.remove(selected);
            commentaireTable.refresh();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de supprimer le commentaire !");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
