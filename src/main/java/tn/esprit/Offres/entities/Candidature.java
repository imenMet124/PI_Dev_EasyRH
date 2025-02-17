package tn.esprit.Offres.entities;

import java.time.LocalDate;

public class Candidature {

    private int idCandidature;
    private Candidat candidat; // Référence à l'objet Candidat
    private Offre offre; // Référence à l'objet Offre

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

    // Constructeur
    public Candidature(int idCandidature, Candidat candidat, Offre offre, LocalDate dateCandidature,
                       StatutCandidature statutCandidature, int noteCandidat, String commentaires,
                       LocalDate dateEntretien, ResultatEntretien resultatEntretien, EtapeCandidature etapeActuelle,
                       LocalDate dateMiseAJourStatut, String recruteurResponsable) {
        this.idCandidature = idCandidature;
        this.candidat = candidat;
        this.offre = offre;
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

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
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