package tn.esprit.Offres.services;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.utils.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat implements IService<Candidat> {
    private Connection connection;
    public ServiceCandidat(){
        connection = Base.getInstance().getConnection();
    }

    @Override
    public void ajouter(Candidat candidat) throws SQLException {
        String sql = "INSERT INTO `candidat`(`nom_candidat`, `prenom_candidat`, `email_candidat`, `telephone_candidat`, `poste_actuel`, `department`, `experience_interne`, `competence`, `statu_candidat`, `disponibilite`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, candidat.getNom_candidat());
        preparedStatement.setString(2, candidat.getPrenom_candidat());
        preparedStatement.setString(3, candidat.getEmail_candidat());
        preparedStatement.setString(4, candidat.getTelephone_candidat());
        preparedStatement.setString(5, candidat.getPoste_actuel());
        preparedStatement.setString(6, candidat.getDepartment());
        preparedStatement.setString(7, candidat.getExperience_interne());
        preparedStatement.setString(8, candidat.getCompetence());
        preparedStatement.setString(9, candidat.getStatu_candidat());
        preparedStatement.setString(10, candidat.getDisponibilite());

        preparedStatement.executeUpdate();  // Exécuter l'insertion
    }

    @Override
    public void modifier(Candidat candidat) throws SQLException {
        String sql = "UPDATE `candidat` SET `nom_candidat` = ?, `prenom_candidat` = ?, `email_candidat` = ?, `telephone_candidat` = ?, `poste_actuel` = ?, `department` = ?, `experience_interne` = ?, `competence` = ?, `statu_candidat` = ?, `disponibilite` = ? "
                + "WHERE `id_candidat` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, candidat.getNom_candidat());
        preparedStatement.setString(2, candidat.getPrenom_candidat());
        preparedStatement.setString(3, candidat.getEmail_candidat());
        preparedStatement.setString(4, candidat.getTelephone_candidat());
        preparedStatement.setString(5, candidat.getPoste_actuel());
        preparedStatement.setString(6, candidat.getDepartment());
        preparedStatement.setString(7, candidat.getExperience_interne());
        preparedStatement.setString(8, candidat.getCompetence());
        preparedStatement.setString(9, candidat.getStatu_candidat());
        preparedStatement.setString(10, candidat.getDisponibilite());
        preparedStatement.setInt(11, candidat.getId_candidat());

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
        String sql = "SELECT * FROM `candidat`";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            candidats.add(new Candidat(
                    rs.getInt("id_candidat"),
                    rs.getString("nom_candidat"),
                    rs.getString("prenom_candidat"),
                    rs.getString("email_candidat"),
                    rs.getString("telephone_candidat"),
                    rs.getString("poste_actuel"),
                    rs.getString("department"),
                    rs.getString("experience_interne"),
                    rs.getString("competence"),
                    rs.getString("statu_candidat"),
                    rs.getString("disponibilite")
            ));
        }

        return candidats;
    }
}
