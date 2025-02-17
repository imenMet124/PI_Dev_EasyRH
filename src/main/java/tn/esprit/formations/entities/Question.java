package tn.esprit.formations.entities;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private String texte;
    private type type;
    private List<Reponses> options= new ArrayList<>();
    private int idQuiz;

    enum type{RADIO, CHECKBOX}
}
