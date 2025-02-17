package tn.esprit.formations.entities;



import tn.esprit.formations.services.ServiceFormation;

import java.sql.SQLException;
import java.util.*;

public class Inscription {
    private int id;
    private int idUtilisateur; // a remplacer par objet User plus tard
    private int idFormation;
    private Date date_inscription;
    private Statue statue;
    private double progress;
    private Set<Integer> modulesCompletes = new HashSet<>();
    private Map<Integer, Integer> scoresQuiz = new HashMap<>(); // <ID_Quiz, Score>
    private boolean certified = false;
    public enum Statue{
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public void ajouterModuleComplete(int moduleId) {
        modulesCompletes.add(moduleId);
        calculerProgress();
    }

    public double calculerProgress() {
        try {
            ServiceFormation serviceFormation = new ServiceFormation();
            Formation formation = serviceFormation.getFormationById(idFormation);

            if (formation != null) {
                int totalModules = formation.getModules().size();
                return totalModules > 0 ? (modulesCompletes.size() * 100.0) / totalModules : 0;
            }
            return 0; // formation non truvee
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; //cas d'erreur
        }
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

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public Statue getStatue() {
        return statue;
    }

    public void setStatue(Statue statue) {
        this.statue = statue;
    }
}
