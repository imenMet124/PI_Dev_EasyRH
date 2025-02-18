package tn.esprit.formations.services;

import tn.esprit.formations.entities.Formation;
import tn.esprit.formations.utils.MyDatabase;
import tn.esprit.formations.entities.Module;

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
        String sql = "INSERT INTO `formations` (titre, description) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, formation.getTitre());
        stmt.setString(2, formation.getDescription());
        stmt.executeUpdate();

        System.out.println("Formation ajoutée avec succès !");

        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                formation.setId(rs.getInt(1)); // MAJ avec l'id generated
            }
        }
    }

    @Override
    public void modifier(Formation formation) throws SQLException {
        String sql = "UPDATE `formations` SET `titre` =?, `description` =? WHERE `id` =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,formation.getTitre());
        preparedStatement.setString(2, formation.getDescription());
        preparedStatement.setInt(3,formation.getId());

        preparedStatement.executeUpdate();
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
        String sql = "SELECT id, titre, description FROM formations";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                formations.add(new Formation(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description")
                ));
            }
        }
        return formations;
    }

    public Formation getFormationById(int id) throws SQLException {
        String sql = "SELECT * FROM formations WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Formation f = new Formation(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("description")
                    );

                    // Charger modules
                    f.setModules(chargerModules(f.getId()));
                    return f;
                }
                return null;
            }
        }
    }

    private List<Module> chargerModules(int formationId) throws SQLException {
        List<Module> modules = new ArrayList<>();

        String sql = "SELECT m.id, m.titre, m.description, m.document_path " +
                "FROM modules m " +
                "INNER JOIN modules_formations mf ON m.id = mf.modules_id " +
                "WHERE mf.formations_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, formationId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Module module = new Module();
                    module.setId(rs.getInt("id"));
                    module.setTitre(rs.getString("titre"));
                    module.setDescription(rs.getString("description"));
                    module.setContenu(rs.getString("document_path"));

                    modules.add(module);
                }
            }
        }
        return modules;
    }

    public void associerUnQuiz(int idFormation, int idQuiz) throws SQLException {
        String sql = "UPDATE formations SET quiz_id = ? WHERE id = ?";
        String sql2 = "UPDATE quiz SET formation_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idQuiz);
            stmt.setInt(2, idFormation);
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt2 = connection.prepareStatement(sql2)){
            stmt2.setInt(2, idQuiz);
            stmt2.setInt(1, idFormation);
            stmt2.executeUpdate();
        }
    }

    private void insererModulesFormation(int formationId, List<Module> modules) throws SQLException {
        String sqlJoin = "INSERT INTO modules_formations (formation_id, module_id) VALUES (?, ?)";
        try (PreparedStatement psJoin = connection.prepareStatement(sqlJoin)) {
            for (Module module : modules) {
                psJoin.setInt(1, formationId);
                psJoin.setInt(2, module.getId());
                psJoin.addBatch();
            }
            psJoin.executeBatch();
        }
    }


}
