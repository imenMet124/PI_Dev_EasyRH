package tn.esprit.Offres.services;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.entities.Candidature;
import tn.esprit.Offres.entities.Offre;
import tn.esprit.Offres.utils.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCandidature implements IService<Candidature> {
    private Connection connection;

    public ServiceCandidature() {
        connection = Base.getInstance().getConnection();
    }

    public void ajouter(Candidature candidature) throws SQLException {
        if (candidature.getCandidat() == null || candidature.getOffre() == null) {
            throw new SQLException("Le candidat et l'offre doivent être spécifiés !");
        }

        String sql = "INSERT INTO candidature (idCandidat, nom, prenom, email, phone, " +
                "position, departement, experienceInterne, competence, statuCandidat, disponibilite, " +
                "idOffre, titreOffre, " + // ✅ Correction : titrePoste au lieu de titreOffre
                "`dateCandidature`, statuCandidature, noteCandidat, commentaires, `dateEntretien`, " + // ✅ Ajout des backticks (`) pour éviter les conflits
                "resultatEntretien, etapeActuelle, `dateMiseAJourStatut`, recruteurResponsable) " + // ✅ Backticks sur les champs date
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // 🟢 Insérer les données du candidat
            preparedStatement.setInt(1, candidature.getCandidat().getIdCandidat());
            preparedStatement.setString(2, candidature.getCandidat().getNom());
            preparedStatement.setString(3, candidature.getCandidat().getPrenom());
            preparedStatement.setString(4, candidature.getCandidat().getEmail());
            preparedStatement.setString(5, candidature.getCandidat().getPhone());
            preparedStatement.setString(6, candidature.getCandidat().getPosition());
            preparedStatement.setString(7, candidature.getCandidat().getDepartment());
            preparedStatement.setString(8, candidature.getCandidat().getExperienceInterne());
            preparedStatement.setString(9, candidature.getCandidat().getCompetence());
            preparedStatement.setString(10, candidature.getCandidat().getStatuCandidat().name());
            preparedStatement.setString(11, candidature.getCandidat().getDisponibilite().name());

            // 🟢 Insérer les données de l’offre
            preparedStatement.setInt(12, candidature.getOffre().getIdOffre());
            preparedStatement.setString(13, candidature.getOffre().getTitrePoste());

            // 🟢 Insérer les données spécifiques à la candidature
            preparedStatement.setDate(14, Date.valueOf(candidature.getDateCandidature()));
            preparedStatement.setString(15, candidature.getStatutCandidature().name());
            preparedStatement.setInt(16, candidature.getNoteCandidat());
            preparedStatement.setString(17, candidature.getCommentaires());
            preparedStatement.setDate(18, candidature.getDateEntretien() != null ? Date.valueOf(candidature.getDateEntretien()) : null);
            preparedStatement.setString(19, candidature.getResultatEntretien().name());
            preparedStatement.setString(20, candidature.getEtapeActuelle().name());
            preparedStatement.setDate(21, candidature.getDateMiseAJourStatut() != null ? Date.valueOf(candidature.getDateMiseAJourStatut()) : null);
            preparedStatement.setString(22, candidature.getRecruteurResponsable());

            // 🟢 Exécution de la requête
            preparedStatement.executeUpdate();

            // Récupérer l'ID auto-généré
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    candidature.setIdCandidature(generatedKeys.getInt(1));
                    System.out.println("✅ Candidature ajoutée avec succès ! ID : " + candidature.getIdCandidature());
                }
            }
        }
    }



    @Override
    public void modifier(Candidature candidature) throws SQLException {
        String sql = "UPDATE candidature SET statuCandidature = ?, noteCandidat = ?, commentaires = ?, " +
                "dateEntretien = ?, resultatEntretien = ?, etapeActuelle = ?, dateMiseAJourStatut = ?, " +
                "recruteurResponsable = ? WHERE idCandidature = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM candidature WHERE idCandidature = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Candidature> afficher() throws SQLException {
        List<Candidature> candidatures = new ArrayList<>();
        String sql = "SELECT c.*, ca.*, o.* " +
                "FROM candidature c " +
                "JOIN candidat ca ON c.idCandidat = ca.idCandidat " +
                "JOIN offre_emploi o ON c.idOffre = o.idOffre";

        Map<Integer, Candidat> candidatsMap = new HashMap<>();
        Map<Integer, Offre> offresMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int idCandidat = rs.getInt("idCandidat");
                int idOffre = rs.getInt("idOffre");

                Candidat candidat = candidatsMap.computeIfAbsent(idCandidat, id -> {
                    try {
                        return new Candidat(
                                id,
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
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                Offre offre = offresMap.computeIfAbsent(idOffre, id -> {
                    try {
                        return new Offre(
                                id,
                                rs.getString("titrePoste"),
                                rs.getString("description"),
                                rs.getDate("datePublication"),
                                rs.getString("statut"),
                                rs.getString("departement"),
                                rs.getString("recruteurResponsable")
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

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
