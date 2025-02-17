package tn.esprit.formations.entities;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private quiz_type type;
    private List<Question> questions = new ArrayList<>();
    private int scoreMinReussite;

    enum quiz_type{MODULE,FORMATION}

    // Constructeur par défaut
    public Quiz() {}

    // Constructeur avec paramètres
    public Quiz(int id, List<Question> questions, int scoreMinReussite) {
        this.id = id;
        this.questions = questions;
        this.scoreMinReussite = scoreMinReussite;
    }

    public boolean estReussi(int scoreUtilisateur) {
        return scoreUtilisateur >= scoreMinReussite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getScoreMinReussite() {
        return scoreMinReussite;
    }

    public void setScoreMinReussite(int scoreMinReussite) {
        this.scoreMinReussite = scoreMinReussite;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", questions=" + questions.size() +
                ", scoreMinReussite=" + scoreMinReussite +
                '}';
}}
/* QuizzApp
   public static class QuizApp extends Application {
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
 */