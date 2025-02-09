package tn.esprit.formations.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certification {
    private int id;
    private int idUtilisateur;
    private Formation formation;
    private LocalDate dateObtention;

    // Formatage pour l'affichage
    public String getDateFormatee() {
        return dateObtention.format(DateTimeFormatter.ISO_DATE);
    }
}
