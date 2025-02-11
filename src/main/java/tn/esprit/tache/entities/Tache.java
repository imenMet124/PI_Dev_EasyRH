package tn.esprit.tache.entities;

import java.util.Date;

/**
 * Classe représentant une tâche dans la gestion des tâches.
 */
public class Tache {
    private int id;
    private String titre;
    private String description;
    private String priorite;
    private String statut;
    private Date deadline;

    public Tache(int id, String titre, String description, String priorite, String statut, Date deadline) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public String getPriorite() { return priorite; }
    public String getStatut() { return statut; }
    public Date getDeadline() { return deadline; }

    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Tache{" + "id=" + id + ", titre='" + titre + '\'' + ", statut='" + statut + "'}";
    }
}
