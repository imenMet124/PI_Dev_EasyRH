package tn.esprit.Offres.entities;

import java.time.LocalDate;

public class Candidature {

    private int idCandidature;

    // Informations du candidat
    private int idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String position;
    private String department;
    private String experienceInterne;
    private String competence;
    private StatuCandidat statuCandidat;
    private Disponibilite disponibilite;

    // Informations de l'offre
    private int idOffre;
    private String titreOffre;

    // Informations de la candidature
    private LocalDate dateCandidature;
    private StatutCandidature statutCandidature;
    private int noteCandidat; // Note sur 100
    private String commentaires;
    private LocalDate dateEntretien;
    private ResultatEntretien resultatEntretien;
    private EtapeCandidature etapeActuelle;
    private LocalDate dateMiseAJourStatut;
    private String recruteurResponsable;

    public Candidature() {

    }

    // Enums
    public enum StatutCandidature {
        EN_ATTENTE, EN_COURS, ACCEPTE, REFUSE
    }

    public enum ResultatEntretien {
        REUSSI, ECHOUE, EN_ATTENTE
    }

    public enum EtapeCandidature {
        PRESELECTION, ENTRETIEN_TECHNIQUE, ENTRETIEN_FINAL
    }

    public enum StatuCandidat {
        EN_ATTENTE, EN_COURS, SHORTLISTE, ENTREVUE, ACCEPTE, REFUSE
    }

    public enum Disponibilite {
        IMMEDIATE, UN_MOIS, DEUX_MOIS, TROIS_MOIS
    }

    // Constructeur
    public Candidature(int idCandidature, int idCandidat, String nom, String prenom, String email, String phone,
                       String position, String department, String experienceInterne, String competence,
                       StatuCandidat statuCandidat, Disponibilite disponibilite, int idOffre, String titreOffre,
                       LocalDate dateCandidature, StatutCandidature statutCandidature, int noteCandidat,
                       String commentaires, LocalDate dateEntretien, ResultatEntretien resultatEntretien,
                       EtapeCandidature etapeActuelle, LocalDate dateMiseAJourStatut, String recruteurResponsable) {
        this.idCandidature = idCandidature;
        this.idCandidat = idCandidat;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.experienceInterne = experienceInterne;
        this.competence = competence;
        this.statuCandidat = statuCandidat;
        this.disponibilite = disponibilite;
        this.idOffre = idOffre;
        this.titreOffre = titreOffre;
        this.dateCandidature = dateCandidature;
        this.statutCandidature = statutCandidature;
        this.noteCandidat = noteCandidat;
        this.commentaires = commentaires;
        this.dateEntretien = dateEntretien;
        this.resultatEntretien = resultatEntretien;
        this.etapeActuelle = etapeActuelle;
        this.dateMiseAJourStatut = dateMiseAJourStatut;
        this.recruteurResponsable = recruteurResponsable;
    }

    // Getters et Setters
    public int getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(int idCandidature) {
        this.idCandidature = idCandidature;
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExperienceInterne() {
        return experienceInterne;
    }

    public void setExperienceInterne(String experienceInterne) {
        this.experienceInterne = experienceInterne;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public StatuCandidat getStatuCandidat() {
        return statuCandidat;
    }

    public void setStatuCandidat(StatuCandidat statuCandidat) {
        this.statuCandidat = statuCandidat;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getTitreOffre() {
        return titreOffre;
    }

    public void setTitreOffre(String titreOffre) {
        this.titreOffre = titreOffre;
    }

    public LocalDate getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(LocalDate dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public StatutCandidature getStatutCandidature() {
        return statutCandidature;
    }

    public void setStatutCandidature(StatutCandidature statutCandidature) {
        this.statutCandidature = statutCandidature;
    }

    public int getNoteCandidat() {
        return noteCandidat;
    }

    public void setNoteCandidat(int noteCandidat) {
        this.noteCandidat = noteCandidat;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDate dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public ResultatEntretien getResultatEntretien() {
        return resultatEntretien;
    }

    public void setResultatEntretien(ResultatEntretien resultatEntretien) {
        this.resultatEntretien = resultatEntretien;
    }

    public EtapeCandidature getEtapeActuelle() {
        return etapeActuelle;
    }

    public void setEtapeActuelle(EtapeCandidature etapeActuelle) {
        this.etapeActuelle = etapeActuelle;
    }

    public LocalDate getDateMiseAJourStatut() {
        return dateMiseAJourStatut;
    }

    public void setDateMiseAJourStatut(LocalDate dateMiseAJourStatut) {
        this.dateMiseAJourStatut = dateMiseAJourStatut;
    }

    public String getRecruteurResponsable() {
        return recruteurResponsable;
    }

    public void setRecruteurResponsable(String recruteurResponsable) {
        this.recruteurResponsable = recruteurResponsable;
    }
}
