package tn.esprit.formations.interfaces;

import tn.esprit.formations.entities.Module;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.formations.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleApp extends Application {
    private ObservableList<Module> modules = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        TextField titreField = new TextField();
        titreField.setPromptText("Titre du module");

        TextField contenuField = new TextField();
        contenuField.setPromptText("Chemin du contenu");

        Button addButton = new Button("Ajouter Module");
        ListView<Module> listView = new ListView<>(modules);

        addButton.setOnAction(e -> {
            String titre = titreField.getText();
            String contenu = contenuField.getText();
            if (!titre.isEmpty() && !contenu.isEmpty()) {
                Module module = new Module(0, titre, contenu, 0);
                ajouterModule(module);
                rafraichirListe();
            }
        });

        rafraichirListe();

        VBox root = new VBox(10, titreField, contenuField, addButton, listView);
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setTitle("Gestion des Modules - Application Desktop");
        primaryStage.show();
    }

    private void ajouterModule(Module module) {
        String sql = "INSERT INTO modules (titre, contenu) VALUES (?, ?)";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, module.getTitre());
            stmt.setString(2, module.getContenu());
            stmt.executeUpdate();
            System.out.println("Module ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rafraichirListe() {
        modules.clear();
        String sql = "SELECT * FROM modules";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getInt("id"));
                module.setTitre(rs.getString("titre"));
                module.setContenu(rs.getString("contenu"));
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

