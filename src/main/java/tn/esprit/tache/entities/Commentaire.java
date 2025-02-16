package tn.esprit.tache.entities;
import java.util.Date;

public class Commentaire {
    private int id;
    private String contenu;
    private Date date;
    private int auteur;
    private int idTache;

    public Commentaire(int id, String contenu, Date date, int auteur, int idTache) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.auteur = auteur;
        this.idTache = idTache;
    }

    public int getId() { return id; }
    public String getContenu() { return contenu; }
    public Date getDate() { return date; }
    public int getAuteur() { return auteur; }
    public int getIdTache() { return idTache; }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", contenu='" + contenu + "', auteur=" + auteur + "}";
    }
}
