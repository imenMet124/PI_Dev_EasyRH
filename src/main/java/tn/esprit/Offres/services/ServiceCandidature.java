package tn.esprit.Offres.services;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.entities.Candidature;
import tn.esprit.Offres.entities.Offre;
import tn.esprit.Offres.utils.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidature implements IService<Candidature> {
    private Connection connection;

    public ServiceCandidature() {
        connection = Base.getInstance().getConnection();
    }

    @Override


    public void ajouter(Candidature candidature) throws SQLException {
        // Validation des entrées
        if (candidature.getCandidat() == null || candidature.getOffre() == null) {
            throw new SQLException("Le candidat et l'offre doivent être spécifiés !");
        }

        // Récupérer les objets Candidat et Offre
        Candidat candidat = candidature.getCandidat();
        Offre offre = candidature.getOffre();

        // Ajouter la candidature à la base de données
        String sql = "INSERT INTO candidature (idCandidat, idOffre, dateCandidature, statuCandidature, noteCandidat, commentaires, dateEntretien, resultatEntretien, etapeActuelle, dateMiseAJourStatut, recruteurResponsable) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, candidat.getIdCandidat());
            preparedStatement.setInt(2, offre.getIdOffre());
            preparedStatement.setDate(3, Date.valueOf(candidature.getDateCandidature()));
            preparedStatement.setString(4, candidature.getStatutCandidature().name());
            preparedStatement.setInt(5, candidature.getNoteCandidat());
            preparedStatement.setString(6, candidature.getCommentaires());
            preparedStatement.setDate(7, candidature.getDateEntretien() != null ? Date.valueOf(candidature.getDateEntretien()) : null);
            preparedStatement.setString(8, candidature.getResultatEntretien().name());
            preparedStatement.setString(9, candidature.getEtapeActuelle().name());
            preparedStatement.setDate(10, candidature.getDateMiseAJourStatut() != null ? Date.valueOf(candidature.getDateMiseAJourStatut()) : null);
            preparedStatement.setString(11, candidature.getRecruteurResponsable());

            preparedStatement.executeUpdate();

            // Récupérer l'ID auto-généré
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    candidature.setIdCandidature(generatedKeys.getInt(1));
                    System.out.println("Candidature ajoutée avec succès ! ID : " + candidature.getIdCandidature());
                }
            }
        }
    }




    @Override
    public void modifier(Candidature candidature) throws SQLException {
        String sql = "UPDATE candidature SET statuCandidature = ?, noteCandidat = ?, commentaires = ?, dateEntretien = ?, resultatEntretien = ?, etapeActuelle = ?, dateMiseAJourStatut = ?, recruteurResponsable = ? " +
                "WHERE idCandidature = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, candidature.getStatutCandidature().name());
        preparedStatement.setInt(2, candidature.getNoteCandidat());
        preparedStatement.setString(3, candidature.getCommentaires());
        preparedStatement.setDate(4, candidature.getDateEntretien() != null ? Date.valueOf(candidature.getDateEntretien()) : null);
        preparedStatement.setString(5, candidature.getResultatEntretien().name());
        preparedStatement.setString(6, candidature.getEtapeActuelle().name());
        preparedStatement.setDate(7, candidature.getDateMiseAJourStatut() != null ? Date.valueOf(candidature.getDateMiseAJourStatut()) : null);
        preparedStatement.setString(8, candidature.getRecruteurResponsable());
        preparedStatement.setInt(9, candidature.getIdCandidature());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM candidature WHERE idCandidature = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override


    public List<Candidature> afficher() throws SQLException {
        List<Candidature> candidatures = new ArrayList<>();
        String sql = "SELECT c.*, ca.*, o.* " +
                "FROM candidature c " +
                "JOIN candidat ca ON c.idCandidat = ca.idCandidat " +
                "JOIN offre_emploi o ON c.idOffre = o.idOffre";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                // Créer l'objet Candidat
                Candidat candidat = new Candidat(
                        rs.getInt("idCandidat"),
                        rs.getString("nomCandidat"),
                        rs.getString("prenomCandidat"),
                        rs.getString("emailCandidat"),
                        rs.getString("telephoneCandidat"),
                        rs.getString("posteActuel"),
                        rs.getString("departement"),
                        rs.getString("experienceInterne"),
                        rs.getString("competence"),
                        Candidat.StatuCandidat.valueOf(rs.getString("statuCandidat")),
                        Candidat.Disponibilite.valueOf(rs.getString("disponibilite"))
                );

                // Créer l'objet Offre
                Offre offre = new Offre(
                        rs.getInt("idOffre"),
                        rs.getString("titrePoste"),
                        rs.getString("description"),
                        rs.getDate("datePublication").toLocalDate(),
                        rs.getString("statut"),
                        rs.getString("departement"),
                        rs.getString("recruteurResponsable")
                );

                // Créer l'objet Candidature
                Candidature candidature = new Candidature(
                        rs.getInt("idCandidature"),
                        candidat,
                        offre,
                        rs.getDate("dateCandidature").toLocalDate(),
                        Candidature.StatutCandidature.valueOf(rs.getString("statuCandidature")),
                        rs.getInt("noteCandidat"),
                        rs.getString("commentaires"),
                        rs.getDate("dateEntretien") != null ? rs.getDate("dateEntretien").toLocalDate() : null,
                        Candidature.ResultatEntretien.valueOf(rs.getString("resultatEntretien")),
                        Candidature.EtapeCandidature.valueOf(rs.getString("etapeActuelle")),
                        rs.getDate("dateMiseAJourStatut") != null ? rs.getDate("dateMiseAJourStatut").toLocalDate() : null,
                        rs.getString("recruteurResponsable")
                );

                candidatures.add(candidature);
            }
        }

        return candidatures;
    }
}
