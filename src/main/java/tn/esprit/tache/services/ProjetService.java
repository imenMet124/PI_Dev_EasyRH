package tn.esprit.tache.services;

import tn.esprit.tache.entities.Projet;
import tn.esprit.tache.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetService implements IProjetService {

    @Override
    public void ajouter(Projet p) throws SQLException {
        String query = "INSERT INTO projet (nom, description, statut, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, p.getNom());
        ps.setString(2, p.getDescription());
        ps.setString(3, p.getStatut());
        ps.setDate(4, new Date(p.getDateDebut().getTime()));
        ps.setDate(5, new Date(p.getDateFin().getTime()));
        ps.executeUpdate();
    }

    @Override
    public List<Projet> afficher() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projet";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Projet p = new Projet(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("statut"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin")
            );
            projets.add(p);
        }
        return projets;
    }

    @Override
    public void modifier(Projet p) throws SQLException {
        String query = "UPDATE projet SET nom=?, description=?, statut=?, date_debut=?, date_fin=? WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, p.getNom());
        ps.setString(2, p.getDescription());
        ps.setString(3, p.getStatut());
        ps.setDate(4, new Date(p.getDateDebut().getTime()));
        ps.setDate(5, new Date(p.getDateFin().getTime()));
        ps.setInt(6, p.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM projet WHERE id=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
