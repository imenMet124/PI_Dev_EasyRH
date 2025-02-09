package tn.esprit.formations.entities;

import java.util.List;

public class Question {
    private int id;
    private String texte;
    private List<String> optionsReponse; // Vide pour questions ouvertes
    private String reponseCorrecte;

    // Méthode pour vérifier la réponse
    public boolean estCorrecte(String reponse) {
        return reponseCorrecte.equalsIgnoreCase(reponse);
    }
}
