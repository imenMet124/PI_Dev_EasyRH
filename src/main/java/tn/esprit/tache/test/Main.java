package tn.esprit.tache.test;

import tn.esprit.tache.entities.Tache;
import tn.esprit.tache.services.ITacheService;
import tn.esprit.tache.services.TacheService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Classe principale pour tester les opérations CRUD sur les tâches.
 */
public class Main {
    public static void main(String[] args) {
        ITacheService service = new TacheService();  // On utilise l'interface ici !

        try {
            //  Ajouter une tâche
            System.out.println("\n➤ Ajout d'une nouvelle tâche...");
            Tache t1 = new Tache(0, "Développer API", "Créer endpoints REST", "élevée", "A faire", new Date());
            service.ajouterTache(t1);
            System.out.println(" Tâche ajoutée avec succès !");

            //  Afficher toutes les tâches
            System.out.println("\n➤ Affichage des tâches...");
            List<Tache> taches = service.afficherTaches();
            if (taches.isEmpty()) {
                System.out.println(" Aucune tâche trouvée.");
            } else {
                taches.forEach(System.out::println);
            }

            //  Modifier une tâche (Changer son statut)
            if (!taches.isEmpty()) {
                int idTache = taches.get(0).getId();
                System.out.println("\n➤ Modification de la tâche ID " + idTache + "...");
                service.modifierTache(idTache, "Terminé");
                System.out.println(" Tâche mise à jour !");
            }

            //  Supprimer une tâche
            if (!taches.isEmpty()) {
                int idTache = taches.get(0).getId();
                System.out.println("\n Suppression de la tâche ID " + idTache + "...");
                service.supprimerTache(idTache);
                System.out.println(" Tâche supprimée !");
            }

        } catch (SQLException ex) {
            System.out.println(" Erreur SQL : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
