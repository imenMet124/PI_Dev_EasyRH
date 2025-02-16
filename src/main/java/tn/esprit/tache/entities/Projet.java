package tn.esprit.tache.entities;
import java.util.Date;

public class Projet {
    private int id;
    private String nom;
    private String description;
    private String statut;
    private Date dateDebut;
    private Date dateFin;

    public Projet(int id, String nom, String description, String statut, Date dateDebut, Date dateFin) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getStatut() { return statut; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Projet{" + "id=" + id + ", nom='" + nom + "', statut='" + statut + "'}";
    }
}