package tn.esprit.Offres.entities;

public class Candidat {
    private int id_candidat;
    private String nom_candidat;
    private String prenom_candidat;
    private String email_candidat;
    private String telephone_candidat;
    private String poste_actuel;
    private String department;
    private String experience_interne;
    private String competence;
    private StatuCandidat statu_candidat;
    private Disponibilite disponibilite;

    public enum StatuCandidat {
        EN_ATTENTE, EN_COURS, SHORTLISTE, ENTREVUE, ACCEPTE, REFUSE
    }

    public enum Disponibilite {
        IMMEDIATE, UN_MOIS, DEUX_MOIS, TROIS_MOIS
    }

    // Constructeurs, getters et setters
    public Candidat() {
    }

    public Candidat(int id_candidat, String nom_candidat, String prenom_candidat, String email_candidat, String telephone_candidat, String poste_actuel, String department, String experience_interne, String competence, StatuCandidat statu_candidat, Disponibilite disponibilite) {
        this.id_candidat = id_candidat;
        this.nom_candidat = nom_candidat;
        this.prenom_candidat = prenom_candidat;
        this.email_candidat = email_candidat;
        this.telephone_candidat = telephone_candidat;
        this.poste_actuel = poste_actuel;
        this.department = department;
        this.experience_interne = experience_interne;
        this.competence = competence;
        this.statu_candidat = statu_candidat;
        this.disponibilite = disponibilite;
    }

    // Getters et setters pour chaque attribut
    public int getId_candidat() {
        return id_candidat;
    }

    public void setId_candidat(int id_candidat) {
        this.id_candidat = id_candidat;
    }

    public String getNom_candidat() {
        return nom_candidat;
    }

    public void setNom_candidat(String nom_candidat) {
        this.nom_candidat = nom_candidat;
    }

    public String getPrenom_candidat() {
        return prenom_candidat;
    }

    public void setPrenom_candidat(String prenom_candidat) {
        this.prenom_candidat = prenom_candidat;
    }

    public String getEmail_candidat() {
        return email_candidat;
    }

    public void setEmail_candidat(String email_candidat) {
        this.email_candidat = email_candidat;
    }

    public String getTelephone_candidat() {
        return telephone_candidat;
    }

    public void setTelephone_candidat(String telephone_candidat) {
        this.telephone_candidat = telephone_candidat;
    }

    public String getPoste_actuel() {
        return poste_actuel;
    }

    public void setPoste_actuel(String poste_actuel) {
        this.poste_actuel = poste_actuel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExperience_interne() {
        return experience_interne;
    }

    public void setExperience_interne(String experience_interne) {
        this.experience_interne = experience_interne;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public StatuCandidat getStatu_candidat() {
        return statu_candidat;
    }

    public void setStatu_candidat(StatuCandidat statu_candidat) {
        this.statu_candidat = statu_candidat;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "id_candidat=" + id_candidat +
                ", nom_candidat='" + nom_candidat + '\'' +
                ", prenom_candidat='" + prenom_candidat + '\'' +
                ", email_candidat='" + email_candidat + '\'' +
                ", telephone_candidat='" + telephone_candidat + '\'' +
                ", poste_actuel='" + poste_actuel + '\'' +
                ", department='" + department + '\'' +
                ", experience_interne='" + experience_interne + '\'' +
                ", competence='" + competence + '\'' +
                ", statu_candidat=" + statu_candidat +
                ", disponibilite=" + disponibilite +
                '}';
    }
}