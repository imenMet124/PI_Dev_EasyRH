package tn.esprit.tache.services;

import tn.esprit.tache.entities.Employe;
import tn.esprit.tache.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IEmployeService {

    @Override
    public void ajouter(Employe e) throws SQLException {
        String query = "INSERT INTO employe (nom, email, role, position, date_embauche, id_dep) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, e.getNom());
        ps.setString(2, e.getEmail());
        ps.setString(3, e.getRole());
        ps.setString(4, e.getPosition());
        ps.setDate(5, new Date(e.getDateEmbauche().getTime()));
        ps.setInt(6, e.getIdDep());
        ps.executeUpdate();
    }

    @Override
    public List<Employe> afficher() throws SQLException {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM employe";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Employe e = new Employe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("position"),
                    rs.getDate("date_embauche"),
                    rs.getInt("id_dep")
            );
            employes.add(e);
        }
        return employes;
    }

    @Override
    public void modifier(Employe e) throws SQLException {
        String query = "UPDATE employe SET nom=?, email=?, role=?, position=?, date_embauche=?, id_dep=? WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, e.getNom());
        ps.setString(2, e.getEmail());
        ps.setString(3, e.getRole());
        ps.setString(4, e.getPosition());
        ps.setDate(5, new Date(e.getDateEmbauche().getTime()));
        ps.setInt(6, e.getIdDep());
        ps.setInt(7, e.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM employe WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
