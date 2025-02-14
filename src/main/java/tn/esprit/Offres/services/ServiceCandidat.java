package tn.esprit.Offres.services;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.entities.User; // Importez l'entité User
import tn.esprit.Offres.utils.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat implements IService<Candidat> {
    private Connection connection;

    public ServiceCandidat() {
        connection = Base.getInstance().getConnection();
    }

    @Override
    public void ajouter(Candidat candidat) throws SQLException {
        String sql = "INSERT INTO `candidat`(`id_user`, `nom`, `prenom`, `email`, `phone`, `position`, `department`, `experience_interne`, `competence`, `statu_candidat`, `disponibilite`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, candidat.getUser().getIdEmp()); // Référence à l'utilisateur
        preparedStatement.setString(2, candidat.getNom());
        preparedStatement.setString(3, candidat.getPrenom());
        preparedStatement.setString(4, candidat.getEmail());
        preparedStatement.setString(5, candidat.getPhone());
        preparedStatement.setString(6, candidat.getPosition());
        preparedStatement.setString(7, candidat.getDepartment());
        preparedStatement.setString(8, candidat.getExperienceInterne());
        preparedStatement.setString(9, candidat.getCompetence());
        preparedStatement.setString(10, candidat.getStatuCandidat().name());
        preparedStatement.setString(11, candidat.getDisponibilite().name());

        preparedStatement.executeUpdate();  // Exécuter l'insertion

        // Récupérer l'ID auto-incrémenté
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int idCandidat = generatedKeys.getInt(1); // Récupérer l'ID généré
            candidat.setIdCandidat(idCandidat); // Mettre à jour l'objet Candidat avec l'ID généré
        }
    }

    @Override
    public void modifier(Candidat candidat) throws SQLException {
        String sql = "UPDATE `candidat` SET `experience_interne` = ?, `competence` = ?, `statu_candidat` = ?, `disponibilite` = ? "
                + "WHERE `id_candidat` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, candidat.getExperienceInterne());
        preparedStatement.setString(2, candidat.getCompetence());
        preparedStatement.setString(3, candidat.getStatuCandidat().name());
        preparedStatement.setString(4, candidat.getDisponibilite().name());
        preparedStatement.setInt(5, candidat.getIdCandidat());

        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Le candidat a été mis à jour avec succès !");
        } else {
            System.out.println("Aucun candidat trouvé avec cet ID.");
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `candidat` WHERE `id_candidat` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id); // ID du candidat à supprimer

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Le candidat a été supprimé avec succès !");
        } else {
            System.out.println("Aucun candidat trouvé avec cet ID.");
        }
    }

    @Override
    public List<Candidat> afficher() throws SQLException {
        List<Candidat> candidats = new ArrayList<>();
        String sql = "SELECT c.*, u.nomEmp, u.prenom, u.email, u.phone, u.position, u.department " +
                "FROM `candidat` c " +
                "JOIN `user` u ON c.id_user = u.idEmp"; // Jointure avec la table User

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            // Créer un objet User avec les informations de l'utilisateur
            User user = new User(
                    rs.getInt("id_emp"),
                    rs.getString("nom_emp"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("position"),
                    rs.getDouble("salaire"),
                    rs.getDate("date_embauche"),
                    rs.getString("statut_emp"),
                    rs.getInt("id_dep")
            );

            // Créer un objet Candidat avec les informations de l'utilisateur
            Candidat candidat = new Candidat(
                    rs.getInt("id_candidat"), // ID de la candidature
                    user, // Référence à l'utilisateur
                    rs.getString("nom"), // Nom de l'utilisateur (injecté depuis la table user)
                    rs.getString("prenom"), // Prénom de l'utilisateur (injecté depuis la table user)
                    rs.getString("email"), // Email de l'utilisateur (injecté depuis la table user)
                    rs.getString("phone"), // Téléphone de l'utilisateur (injecté depuis la table user)
                    rs.getString("position"), // Poste actuel de l'utilisateur (injecté depuis la table user)
                    rs.getString("department"), // Département de l'utilisateur (injecté depuis la table user)
                    rs.getString("experience_interne"), // Expérience interne
                    rs.getString("competence"), // Compétences
                    Candidat.StatuCandidat.valueOf(rs.getString("statu_candidat")), // Statut de la candidature
                    Candidat.Disponibilite.valueOf(rs.getString("disponibilite")) // Disponibilité
            );

            // Ajouter le candidat à la liste
            candidats.add(candidat);
        }
        return candidats;
    }
}