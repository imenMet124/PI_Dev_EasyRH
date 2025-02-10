package tn.esprit.evenement.entities;


import java.time.LocalDate;

public class Participation {
    private int idParticipation;
    private int idEvenement;
    private int participant;
    private LocalDate dateInscription;
    private String statut; // "Confirmé", "Annulé", "En attente"

    public Participation(int idParticipation, int idEvenement, int participant, LocalDate dateInscription, String statut) {
        this.idParticipation = idParticipation;
        this.idEvenement = idEvenement;
        this.participant = participant;
        this.dateInscription = dateInscription;
        this.statut = statut;
    }
    public Participation(int idEvenement, int participant, LocalDate dateInscription, String statut) {
        this.idEvenement = idEvenement;
        this.participant = participant;
        this.dateInscription = dateInscription;
        this.statut = statut;
    }


    public int getIdParticipation() {
        return idParticipation;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public int getParticipant() {
        return participant;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "idParticipation=" + idParticipation +
                ", idEvenement=" + idEvenement +
                ", participant=" + participant +
                ", dateInscription=" + dateInscription +
                ", statut='" + statut + '\'' +
                '}';
    }
}
