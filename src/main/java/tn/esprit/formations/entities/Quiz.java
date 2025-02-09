package tn.esprit.formations.entities;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private List<Question> questions = new ArrayList<>();
    private int scoreMinReussite;

    public int

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
}
