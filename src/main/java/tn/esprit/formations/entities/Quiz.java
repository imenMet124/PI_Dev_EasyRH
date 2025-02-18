package tn.esprit.formations.entities;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private String titre;
    private quiz_type type;
    private List<Question> questions = new ArrayList<>();
    private int scoreMinReussite;

    enum quiz_type{MODULE,FORMATION}

    public Quiz() {}

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