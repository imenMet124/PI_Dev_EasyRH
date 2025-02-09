package tn.esprit.formations.entities;

public class Module {
    private int id;
    private String titre;
    private String contenu; // chemin vers fichier
    private Quiz quizModule; // Optionnel

    // Getters/Setters
    public Quiz getQuizModule() {
        return quizModule;
    }

    public void setQuizModule(Quiz quiz) {
        this.quizModule = quiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
