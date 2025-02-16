package tn.esprit.Offres.services;


import tn.esprit.Offres.entities.Offre;
import tn.esprit.Offres.utils.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffres implements IService<Offre>{
    private Connection connection;
    public ServiceOffres(){
        connection = Base.getInstance().getConnection();
    }


    @Override
    public void ajouter(Offre offre) throws SQLException {
        String sql = "INSERT INTO `offre_emploi`(`titrePoste`, `description`, `datePublication`, `dateAcceptation`, `timeToHire`, `timeToFill`, `statuOffre`, `departement`, `recruteurResponsable`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, offre.getTitrePoste());
        preparedStatement.setString(2, offre.getDescription());
        preparedStatement.setDate(3, offre.getDatePublication());  // Si c'est un Date (java.sql.Date)
        preparedStatement.setDate(4, offre.getDateAcceptation());
        preparedStatement.setInt(5, offre.getTimeToHire());  // Si c'est un entier
        preparedStatement.setInt(6, offre.getTimeToFill());
        preparedStatement.setString(7, offre.getStatuOffre());
        preparedStatement.setString(8, offre.getDepartement());
        preparedStatement.setString(9, offre.getRecruteurResponsable());

        preparedStatement.executeUpdate();  // Exécuter l'insertion
}

        @Override
    public void modifier(Offre offre) throws SQLException {
            String sql = "UPDATE `offre_emploi` SET `titrePoste` = ?, `description` = ?, `datePublication` = ?, `dateAcceptation` = ?, `timeToHire` = ?, `timeToFill` = ?, `statuOffre` = ?, `departement` = ?, `recruteurResponsable` = ? "
                    + "WHERE `idOffre` = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, offre.getTitrePoste());
            preparedStatement.setString(2, offre.getDescription());
            preparedStatement.setDate(3, offre.getDatePublication());
            preparedStatement.setDate(4, offre.getDateAcceptation());
            preparedStatement.setInt(5, offre.getTimeToHire());
            preparedStatement.setInt(6, offre.getTimeToFill());
            preparedStatement.setString(7, offre.getStatuOffre());
            preparedStatement.setString(8, offre.getDepartement());
            preparedStatement.setString(9, offre.getRecruteurResponsable());
            preparedStatement.setInt(10, offre.getIdOffre());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'offre a été mise à jour avec succès !");
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }
        }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `offre_emploi` WHERE `idOffre` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id); // ID de l'offre à supprimer

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("L'offre a été supprimée avec succès !");
        } else {
            System.out.println("Aucune offre trouvée avec cet ID.");
        }

    }

    @Override
    public List<Offre> afficher() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String sql = " SELECT * FROM `offre_emploi`";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            offres.add(new Offre(
                    rs.getInt("idOffre"),
                    rs.getString("titrePoste"),
                    rs.getString("description"),
                    rs.getDate("datePublication"),
                    rs.getDate("dateAcceptation"),
                    rs.getInt("timeToHire"),
                    rs.getInt("timeToFill"),
                    rs.getString("statuOffre"),
                    rs.getString("departement"),
                    rs.getString("recruteurResponsable")
            ));
        }

        return offres ;
    }
}
