package tn.esprit.formations.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tn.esprit.formations.entities.CompetencesCibles;
import tn.esprit.formations.entities.Formation;
import tn.esprit.formations.utils.MyDatabase;

public class FormationApp extends Application {
    private ObservableList<Formation> formations = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        TextField titreField = new TextField();
        titreField.setPromptText("Titre de la formation");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description de la formation");

        ComboBox<CompetencesCibles> competencesBox = new ComboBox<>();
        competencesBox.getItems().setAll(CompetencesCibles.values());

        CheckBox quizFinalCheckBox = new CheckBox("Quiz final réussi");

        Button addButton = new Button("Ajouter Formation");
        ListView<Formation> listView = new ListView<>(formations);

        // Bouton pour afficher les utilisateurs inscrits
        Button showUsersButton = new Button("Afficher les utilisateurs inscrits");

        // Bouton pour afficher les modules inclus dans une formation
        Button showModulesButton = new Button("Afficher les modules de la formation");

        // Bouton pour afficher toutes les inscriptions d'une formation
        Button showAllInscriptionsButton = new Button("Afficher toutes les inscriptions");

        addButton.setOnAction(e -> {
            String titreValue = titreField.getText();
            String description = descriptionField.getText();
            CompetencesCibles competence = competencesBox.getValue();
            boolean quizFinalReussi = quizFinalCheckBox.isSelected();

            if (!titreValue.isEmpty() && !description.isEmpty() && competence != null) {
                Formation formation = new Formation(titreValue, description);
                ajouterFormation(formation);
                rafraichirListe();
            }
        });

        showUsersButton.setOnAction(e -> {
            Formation selectedFormation = listView.getSelectionModel().getSelectedItem();
            if (selectedFormation != null) {
                List<String> utilisateurs = obtenirInscriptions(selectedFormation.getId());
                afficherListe("Utilisateurs inscrits", utilisateurs);
            } else {
                showAlert("Veuillez sélectionner une formation.");
            }
        });

        showModulesButton.setOnAction(e -> {
            Formation selectedFormation = listView.getSelectionModel().getSelectedItem();
            if (selectedFormation != null) {
                List<String> modules = obtenirModules(selectedFormation.getId());
                afficherListe("Modules inclus", modules);
            } else {
                showAlert("Veuillez sélectionner une formation.");
            }
        });

        showAllInscriptionsButton.setOnAction(e -> {
            List<String> toutesInscriptions = obtenirToutesInscriptions();
            afficherListe("Toutes les inscriptions", toutesInscriptions);
        });

        rafraichirListe();

        VBox root = new VBox(10, titreField, descriptionField, competencesBox, quizFinalCheckBox, addButton, listView, showUsersButton, showModulesButton, showAllInscriptionsButton);
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.setTitle("Gestion des Formations - Application Desktop");
        primaryStage.show();
    }

    private void ajouterFormation(Formation formation) {
        String sql = "INSERT INTO formations (titre, description, competences_cibles, quiz_final_reussi) VALUES (?, ?, ?, ?)";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, formation.getTitre());
            stmt.setString(2, formation.getDescription());
            stmt.setString(3, formation.getCompetencesCibles().toString());
            stmt.setInt(4, formation.getIdQuizFinal());
            stmt.executeUpdate();
            System.out.println("Formation ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rafraichirListe() {
        formations.clear();
        String sql = "SELECT * FROM formations";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setTitre(rs.getString("titre"));
                formation.setDescription(rs.getString("description"));

                String competencesString = rs.getString("competences_cibles");
                List<CompetencesCibles> competences = Arrays.stream(competencesString.split(","))
                        .map(String::trim)
                        .map(CompetencesCibles::valueOf)
                        .collect(Collectors.toList());

                formation.setCompetencesCibles(competences);


                formation.setIdQuizFinal(rs.getInt("quiz_final"));
                formations.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Afficher la liste des utilisateurs inscrits à une formation donnée
    public List<String> obtenirInscriptions(int formationId) {
        List<String> utilisateurs = new ArrayList<>();
        String sql = "SELECT u.nom FROM inscriptions i JOIN users u ON i.user_id = u.id WHERE i.formation_id = ?";

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, formationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                utilisateurs.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    // Afficher la liste des modules inclus dans une formation donnée
    public List<String> obtenirModules(int formationId) {
        List<String> modules = new ArrayList<>();
        String sql = "SELECT m.titre FROM modules m JOIN formation_module fm ON m.id = fm.module_id WHERE fm.formation_id = ?";

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, formationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modules.add(rs.getString("titre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }

    // Afficher toutes les inscriptions de toutes les formations
    public List<String> obtenirToutesInscriptions() {
        List<String> inscriptions = new ArrayList<>();
        String sql = "SELECT f.titre, u.nom FROM inscriptions i JOIN users u ON i.user_id = u.id JOIN formations f ON i.formation_id = f.id";

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                inscriptions.add("Formation: " + rs.getString("titre") + " - Utilisateur: " + rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }

    private void afficherListe(String titre, List<String> elements) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(String.join("\n", elements));
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
