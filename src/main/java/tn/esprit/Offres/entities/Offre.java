package tn.esprit.Offres.entities;

import java.time.LocalDate;
import java.util.Date;


public class Offre {
    private int idOffre;
    private String titrePoste;
    private String description;
    private Date datePublication ;
    private Date dateAcceptation;
    private int timeToHire;
    private int timeToFill;
    private String statuOffre;
    private String departement;
    private String recruteurResponsable;

    public Offre() {
    }
    public Offre(int idOffre, String titrePoste, String description, Date datePublication,
                 String statuOffre, String departement, String recruteurResponsable) {
        this.idOffre = idOffre;
        this.titrePoste = titrePoste;
        this.description = description;
        this.datePublication = datePublication;
        this.statuOffre = statuOffre;
        this.departement = departement;
        this.recruteurResponsable = recruteurResponsable;
    }
    public Offre(String titrePoste, String description, Date datePublication, String statuOffre, String departement, String recruteurResponsable) {
        this.titrePoste = titrePoste;
        this.description = description;
        this.datePublication = datePublication;
        this.statuOffre = statuOffre;
        this.departement = departement;
        this.recruteurResponsable = recruteurResponsable;
    }

    public Offre(String titrePoste, String description, Date datePublication, Date dateAcceptation, int timeToHire, int timeToFill, String statuOffre, String departement, String recruteurResponsable) {
        this.titrePoste = titrePoste;
        this.description = description;
        this.datePublication = datePublication;
        this.dateAcceptation = dateAcceptation;
        this.timeToHire = timeToHire;
        this.timeToFill = timeToFill;
        this.statuOffre = statuOffre;
        this.departement = departement;
        this.recruteurResponsable = recruteurResponsable;
    }

    public Offre(int idOffre, String titrePoste, String description, Date datePublication, Date dateAcceptation, int timeToHire, int timeToFill, String statuOffre, String departement, String recruteurResponsable) {
        this.idOffre = idOffre;
        this.titrePoste = titrePoste;
        this.description = description;
        this.datePublication = datePublication;
        this.dateAcceptation = dateAcceptation;
        this.timeToHire = timeToHire;
        this.timeToFill = timeToFill;
        this.statuOffre = statuOffre;
        this.departement = departement;
        this.recruteurResponsable = recruteurResponsable;
    }

    public Offre(String titrePoste, String description, java.sql.Date datePublication, String statuOffre, String departement, String recruteurResponsable) {
    }



    public int getIdOffre() {
        return idOffre;
    }

    public String getTitrePoste() {
        return titrePoste;
    }

    public java.sql.Date getDatePublication() {
        return (java.sql.Date) (java.util.Date) datePublication;
    }

    public String getDescription() {
        return description;
    }

    public java.sql.Date getDateAcceptation() {
        return (java.sql.Date) (java.util.Date) dateAcceptation;
    }

    public int getTimeToHire() {
        return timeToHire;
    }

    public int getTimeToFill() {
        return timeToFill;
    }

    public String getStatuOffre() {
        return statuOffre;
    }

    public String getDepartement() {
        return departement;
    }

    public String getRecruteurResponsable() {
        return recruteurResponsable;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setTitrePoste(String titrePoste) {
        this.titrePoste = titrePoste;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public void setDateAcceptation(Date dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public void setTimeToHire(int timeToHire) {
        this.timeToHire = timeToHire;
    }

    public void setTimeToFill(int timeToFill) {
        this.timeToFill = timeToFill;
    }

    public void setStatuOffre(String statuOffre) {
        this.statuOffre = statuOffre;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setRecruteurResponsable(String recruteurResponsable) {
        this.recruteurResponsable = recruteurResponsable;
    }

    @Override
    public String toString() {
        return "Offres{" +
                "id=" + idOffre +
                ", titrePoste='" + titrePoste + '\'' +
                ", description='" + description + '\'' +
                ", datePublication=" + datePublication +
                ", dateAcceptation=" + dateAcceptation +
                ", timeToHire=" + timeToHire +
                ", timeToFill=" + timeToFill +
                ", statuOffre='" + statuOffre + '\'' +
                ", departement='" + departement + '\'' +
                ", recruteurResponsable='" + recruteurResponsable + '\'' +
                '}';
    }
}
