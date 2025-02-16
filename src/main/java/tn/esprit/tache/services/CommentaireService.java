package tn.esprit.tache.services;

import tn.esprit.tache.entities.Commentaire;
import tn.esprit.tache.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {

    private Connection cnx = DBConnection.getInstance().getConnection();

    public void ajouter(Commentaire commentaire) throws SQLException {
        String sql = "INSERT INTO commentaires (contenu, auteur_id, id_tache) VALUES (?, ?, ?)";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setString(1, commentaire.getContenu());
        stmt.setInt(2, commentaire.getAuteur());
        stmt.setInt(3, commentaire.getIdTache());
        stmt.executeUpdate();
    }

    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaires";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            commentaires.add(new Commentaire(
                    rs.getInt("id"),
                    rs.getString("contenu"),
                    rs.getTimestamp("date_creation"),
                    rs.getInt("auteur_id"),
                    rs.getInt("id_tache")
            ));
        }
        return commentaires;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM commentaires WHERE id=?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
