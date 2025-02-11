package tn.esprit.tache.services;

import tn.esprit.tache.entities.Tache;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface définissant les opérations CRUD pour les tâches.
 */
public interface ITacheService {
    void ajouterTache(Tache t) throws SQLException;
    List<Tache> afficherTaches() throws SQLException;
    void modifierTache(int id, String newStatut) throws SQLException;
    void supprimerTache(int id) throws SQLException;
}
