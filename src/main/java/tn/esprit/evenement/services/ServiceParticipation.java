package tn.esprit.evenement.services;

import tn.esprit.evenement.utils.MyDataBase;
import tn.esprit.evenement.entities.Participation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipation {
    private Connection connection;

    public ServiceParticipation() {
        this.connection = MyDataBase.getInstance().getConnection();
    }

    // Ajouter une participation
    public void ajouterParticipation(Participation p) {
        String req = "INSERT INTO participation (Id_Evenement, Participant, `Date inscription`, Statut) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, p.getIdEvenement());
            ps.setInt(2, p.getParticipant());
            ps.setDate(3, Date.valueOf(p.getDateInscription()));
            ps.setString(4, p.getStatut());
            ps.executeUpdate();
            System.out.println("Participation ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la participation : " + e.getMessage());
        }
    }

    // Modifier le statut d'une participation
    public void modifierStatut(int idParticipation, String nouveauStatut) {
        String req = "UPDATE participation SET Statut = ? WHERE Id_Participation = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, nouveauStatut);
            ps.setInt(2, idParticipation);
            ps.executeUpdate();
            System.out.println("Statut mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

    // Supprimer une participation
    public void supprimerParticipation(int idParticipation) {
        String req = "DELETE FROM participation WHERE Id_Participation = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, idParticipation);
            ps.executeUpdate();
            System.out.println("Participation supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la participation : " + e.getMessage());
        }
    }

    // Récupérer les participations d'un utilisateur
    public List<Participation> getParticipationsParUtilisateur(int idUser) {
        List<Participation> participations = new ArrayList<>();
        String req = "SELECT * FROM participation WHERE Participant = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                participations.add(new Participation(
                        rs.getInt("Id_Participation"),
                        rs.getInt("Id_Evenement"),
                        rs.getInt("Participant"),
                        rs.getDate("Date inscription").toLocalDate(),
                        rs.getString("Statut")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des participations : " + e.getMessage());
        }
        return participations;
    }
}
