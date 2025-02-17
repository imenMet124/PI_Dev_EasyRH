package tn.esprit.formations.services;

import tn.esprit.formations.entities.Formation;
import tn.esprit.formations.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFormation implements IService<Formation> {
    private Connection connection;

    public ServiceFormation(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Formation formation) throws SQLException{
        String sql = "INSERT INTO 'formations' (titre, description) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, formation.getTitre());
        stmt.setString(2, formation.getDescription());
        stmt.executeUpdate();

        System.out.println("Formation ajoutée avec succès !");
    }

    @Override
    public void modifier(Formation formation) throws SQLException {
        String sql = "UPDATE `formations` SET `titre` =?, `description` =? WHERE `id` =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,formation.getTitre());
        preparedStatement.setString(2, formation.getDescription());
        preparedStatement.setInt(3,formation.getId());
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql ="DELETE FROM `formations` WHERE `id`=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Formation> afficher() throws SQLException {
        List<Formation> formations = new ArrayList<>();
        String sql = "SELECT titre, description FROM `formations`";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            formations.add(new Formation(
                    rs.getString("titre"),
                    rs.getString("description")
            ));
        }
        return formations;
    }

    public Formation getFormationById(int id) throws SQLException{
        String sql = "SELECT * FROM formation WHERE id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            return new Formation(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description")
            );
        } else {
            return null; //aucun formation avec cet id
        }
    }

    public void associerUnQuiz(int id, int idQuiz){

    }
}
