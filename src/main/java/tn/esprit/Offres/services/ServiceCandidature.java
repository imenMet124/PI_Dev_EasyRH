package tn.esprit.Offres.services;

import tn.esprit.Offres.entities.Candidature;
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
        String sql = "INSERT INTO candidature (idCandidat, idOffre, dateCandidature, statutCandidature, noteCandidat, commentaires, dateEntretien, resultatEntretien, etapeActuelle, dateMiseAJourStatut, recruteurResponsable) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, candidature.getIdCandidat());
        preparedStatement.setInt(2, candidature.getIdOffre());
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

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            candidature.setIdCandidature(generatedKeys.getInt(1));
        }
    }

    @Override
    public void modifier(Candidature candidature) throws SQLException {
        String sql = "UPDATE candidature SET statutCandidature = ?, noteCandidat = ?, commentaires = ?, dateEntretien = ?, resultatEntretien = ?, etapeActuelle = ?, dateMiseAJourStatut = ?, recruteurResponsable = ? " +
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
        String sql = "SELECT * FROM candidature";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Candidature candidature = new Candidature(
                    rs.getInt("idCandidature"),
                    rs.getInt("idCandidat"),
                    "", "", "", "", "", "", "", "",  // Placeholder values pour le candidat
                    Candidature.StatuCandidat.EN_ATTENTE,
                    Candidature.Disponibilite.IMMEDIATE,
                    rs.getInt("idOffre"),
                    "",  // Placeholder pour le titre de l'offre
                    rs.getDate("dateCandidature").toLocalDate(),
                    Candidature.StatutCandidature.valueOf(rs.getString("statutCandidature")),
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
        return candidatures;
    }
}
