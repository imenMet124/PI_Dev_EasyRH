package tn.esprit.tache.services;

import tn.esprit.tache.entities.Tache;
import tn.esprit.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de `ITacheService` pour gérer les tâches avec JDBC.
 */
public class TacheService implements ITacheService {

    /**
     * Ajouter une tâche dans la base de données.
     */
    @Override
    public void ajouterTache(Tache t) throws SQLException {
        String query = "INSERT INTO tache (titre_tache, description, priorite, statut_tache, deadline) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTitre());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getPriorite());
            ps.setString(4, t.getStatut());
            ps.setDate(5, new java.sql.Date(t.getDeadline().getTime()));

            ps.executeUpdate();
            System.out.println("Tâche ajoutée avec succès !");
        }
    }

    /**
     * Récupérer toutes les tâches de la base de données.
     */
    @Override
    public List<Tache> afficherTaches() throws SQLException {
        List<Tache> taches = new ArrayList<>();
        String query = "SELECT * FROM tache";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Tache t = new Tache(
                        rs.getInt("id_tache"),
                        rs.getString("titre_tache"),
                        rs.getString("description"),
                        rs.getString("priorite"),
                        rs.getString("statut_tache"),
                        rs.getDate("deadline")
                );
                taches.add(t);
            }
        }
        return taches;
    }

    /**
     * Modifier une tâche existante.
     */
    @Override
    public void modifierTache(int id, String newStatut) throws SQLException {
        String query = "UPDATE tache SET statut_tache=? WHERE id_tache=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newStatut);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Tâche mise à jour avec succès !");
        }
    }

    /**
     * Supprimer une tâche par ID.
     */
    @Override
    public void supprimerTache(int id) throws SQLException {
        String query = "DELETE FROM tache WHERE id_tache=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Tâche supprimée avec succès !");
        }
    }
}
