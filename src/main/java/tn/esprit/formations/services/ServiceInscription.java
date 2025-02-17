package tn.esprit.formations.services;

import tn.esprit.formations.entities.Formation;
import tn.esprit.formations.entities.Inscription;
import tn.esprit.formations.utils.MyDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceInscription implements IService<Inscription> {

    private Connection connection;

    public ServiceInscription(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Inscription inscription) throws SQLException {
        String sql = "INSERT INTO `inscriptions`(`id`,`user_id`,`formation_id`,`date_inscription`,`statue`,`progress`,`certified`) VALUES (?,?,?,?,?,?,?,)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, inscription.getId());
        preparedStatement.setInt(2,inscription.getIdUtilisateur());
        preparedStatement.setInt(3,inscription.getIdFormation());
        preparedStatement.setDate(4, new java.sql.Date(inscription.getDate_inscription().getTime()));
        preparedStatement.setString(5,inscription.getStatue().toString());
        preparedStatement.setDouble(6,0.0);
        preparedStatement.setBoolean(7,false);
    }

    @Override
    public void modifier(Inscription inscription) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Inscription> afficher() throws SQLException {
        return null;
    }
}
