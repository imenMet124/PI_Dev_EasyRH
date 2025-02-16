package tn.esprit.tache.entities;
import java.util.Date;

public class Affectation {
    private int id;
    private int idTache;
    private int idEmploye;
    private Date dateAffectation;

    public Affectation(int id, int idTache, int idEmploye, Date dateAffectation) {
        this.id = id;
        this.idTache = idTache;
        this.idEmploye = idEmploye;
        this.dateAffectation = dateAffectation;
    }

    public int getId() { return id; }
    public int getIdTache() { return idTache; }
    public int getIdEmploye() { return idEmploye; }
    public Date getDateAffectation() { return dateAffectation; }

    @Override
    public String toString() {
        return "Affectation{" + "id=" + id + ", idTache=" + idTache + ", idEmploye=" + idEmploye + "}";
    }
}
