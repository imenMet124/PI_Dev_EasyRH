package tn.esprit.tache.entities;
import java.util.Date;

public class Employe {
    private int id;
    private String nom;
    private String email;
    private String role;
    private String position;
    private Date dateEmbauche;
    private int idDep;

    public Employe(int id, String nom, String email, String role, String position, Date dateEmbauche, int idDep) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.position = position;
        this.dateEmbauche = dateEmbauche;
        this.idDep = idDep;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getPosition() { return position; }
    public Date getDateEmbauche() { return dateEmbauche; }
    public int getIdDep() { return idDep; }

    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom='" + nom + "', role='" + role + "'}";
    }
}
