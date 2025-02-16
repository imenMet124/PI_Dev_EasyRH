package tn.esprit.tache.entities;

public class Departement {
    private int id;
    private String nom;
    private int responsable;

    public Departement(int id, String nom, int responsable) {
        this.id = id;
        this.nom = nom;
        this.responsable = responsable;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getResponsable() { return responsable; }

    @Override
    public String toString() {
        return "Departement{" + "id=" + id + ", nom='" + nom + "', responsable=" + responsable + "}";
    }
}
