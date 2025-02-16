package tn.esprit.tache.services;

import tn.esprit.tache.entities.Affectation;
import tn.esprit.tache.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AffectationService implements IAffectationService {

    @Override
    public void ajouter(Affectation a) throws SQLException {
        String query = "INSERT INTO affectation (id_tache, id_employe, date_affectation) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, a.getIdTache());
        ps.setInt(2, a.getIdEmploye());
        ps.setDate(3, new Date(a.getDateAffectation().getTime()));
        ps.executeUpdate();
    }

    @Override
    public List<Affectation> afficher() throws SQLException {
        List<Affectation> affectations = new ArrayList<>();
        String query = "SELECT * FROM affectation";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Affectation a = new Affectation(
                    rs.getInt("id"),
                    rs.getInt("id_tache"),
                    rs.getInt("id_employe"),
                    rs.getDate("date_affectation")
            );
            affectations.add(a);
        }
        return affectations;
    }

    @Override
    public void modifier(Affectation a) throws SQLException {
        String query = "UPDATE affectation SET id_tache=?, id_employe=?, date_affectation=? WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, a.getIdTache());
        ps.setInt(2, a.getIdEmploye());
        ps.setDate(3, new Date(a.getDateAffectation().getTime()));
        ps.setInt(4, a.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM affectation WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
