package tn.esprit.formations.entities;

import tn.esprit.formations.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certification {
    private int id;
    private int idUtilisateur;
    private int idFormation;
    private String pdf_path;
    private LocalDate dateObtention;

    // Constructeurs
    public Certification() {
    }

    public Certification(int idUtilisateur, int idFormation, LocalDate dateObtention) {
        this.idUtilisateur = idUtilisateur;
        this.idFormation = idFormation;
        this.dateObtention = dateObtention;
    }

    // Getters/Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getFormationId() {
        return idFormation;
    }

    public void setFormationId(int formationId) {
        this.idFormation = formationId;
    }

    public LocalDate getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(LocalDate dateObtention) {
        this.dateObtention = dateObtention;
    }

    // Formatage pour l'affichage
    public String getDateFormatee() {
        return dateObtention.format(DateTimeFormatter.ISO_DATE);
    }


    // Méthode pour générer une certification si la formation est terminée
    public static boolean genererCertification(int idUtilisateur, int formationId) {
        String checkSql = "SELECT quiz_final_reussi FROM formations f " +
                "JOIN inscriptions i ON f.id = i.formation_id " +
                "WHERE i.user_id = ? AND f.id = ?";

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, idUtilisateur);
            checkStmt.setInt(2, formationId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getBoolean("quiz_final_reussi")) {
                String insertSql = "INSERT INTO certifications (user_id, formation_id, date_obtention) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, idUtilisateur);
                    insertStmt.setInt(2, formationId);
                    insertStmt.setDate(3, Date.valueOf(LocalDate.now()));
                    insertStmt.executeUpdate();
                    System.out.println("Certification générée avec succès !");
                    return true;
                }
            } else {
                System.out.println("Formation non terminée ou quiz final non réussi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


