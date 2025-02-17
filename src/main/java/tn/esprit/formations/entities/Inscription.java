package tn.esprit.formations.entities;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Inscription {
    private int id;
    private int idUtilisateur; // a remplacer par objet User plus tard
    private int idFormation;
    private Set<Integer> modulesCompletes = new HashSet<>();
    private Map<Integer, Integer> scoresQuiz = new HashMap<>(); // <ID_Quiz, Score>

    public void ajouterModuleComplete(int moduleId) {
        modulesCompletes.add(moduleId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public void setModulesCompletes(Set<Integer> modulesCompletes) {
        this.modulesCompletes = modulesCompletes;
    }

    public Map<Integer, Integer> getScoresQuiz() {
        return scoresQuiz;
    }

    public void setScoresQuiz(Map<Integer, Integer> scoresQuiz) {
        this.scoresQuiz = scoresQuiz;
    }

    public Set<Integer> getModulesCompletes() {
        return modulesCompletes;
    }
}
