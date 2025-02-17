package tn.esprit.formations.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.formations.entities.Quiz;
import tn.esprit.formations.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.application.Application.launch;

public class QuizApp extends Application {
    private ObservableList<Quiz> quizzes = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        TextField idField = new TextField();
        idField.setPromptText("ID du Quiz");

        TextField scoreField = new TextField();
        scoreField.setPromptText("Score minimal de réussite");

        Button addButton = new Button("Ajouter Quiz");
        ListView<Quiz> listView = new ListView<>(quizzes);

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int scoreMin = Integer.parseInt(scoreField.getText());
                Quiz quiz = new Quiz(id, new ArrayList<>(), scoreMin);
                ajouterQuiz(quiz);
                rafraichirListe();
            } catch (NumberFormatException ex) {
                showAlert("Veuillez entrer des nombres valides pour ID et Score minimum.");
            }
        });

        rafraichirListe();

        VBox root = new VBox(10, idField, scoreField, addButton, listView);
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setTitle("Gestion des Quiz - Application Desktop");
        primaryStage.show();
    }

    private void ajouterQuiz(Quiz quiz) {
        String sql = "INSERT INTO quizzes (id, score_min_reussite) VALUES (?, ?)";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quiz.getId());
            stmt.setInt(2, quiz.getScoreMinReussite());
            stmt.executeUpdate();
            System.out.println("Quiz ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rafraichirListe() {
        quizzes.clear();
        String sql = "SELECT * FROM quizzes";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setScoreMinReussite(rs.getInt("score_min_reussite"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
