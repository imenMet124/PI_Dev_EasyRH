package tn.esprit.Offres.test;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.services.ServiceCandidat;
import tn.esprit.Offres.entities.User;
import tn.esprit.Offres.services.ServiceUser;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialiser les services
        ServiceUser serviceUser = new ServiceUser();
        ServiceCandidat serviceCandidat = new ServiceCandidat();

        try {
            // Créer un nouvel utilisateur
            User user = new User();
            user.setNomEmp("Dupont");

            user.setEmail("jean.dupont@example.com");
            user.setPhone("0612345678");
            user.setRole("Employé");
            user.setPosition("Développeur");
            user.setSalaire(3000.0);
            user.setDateEmbauche(Date.valueOf("2023-01-01")); // Convertir une chaîne en java.sql.Date
            user.setStatutEmp("Actif");
            user.setIdDep(101); // ID du département

            // Ajouter l'utilisateur à la base de données
            serviceUser.ajouter(user);
            System.out.println("Utilisateur créé avec succès !");

            // Récupérer l'utilisateur créé par son email
            User userCree = serviceUser.getUserById(1);
            if (userCree == null) {
                System.err.println("Erreur : Utilisateur non trouvé après création.");
                return;
            }

            // Créer une nouvelle candidature pour cet utilisateur
            Candidat candidat = new Candidat();
            candidat.setUser(userCree);
            candidat.setNom(userCree.getNomEmp()); // Injecter le nom de l'utilisateur

            candidat.setEmail(userCree.getEmail()); // Injecter l'email de l'utilisateur
            candidat.setPhone(userCree.getPhone()); // Injecter le téléphone de l'utilisateur
            candidat.setPosition(userCree.getPosition()); // Injecter le poste actuel de l'utilisateur

            candidat.setExperienceInterne("/path/to/experience.pdf");
            candidat.setCompetence("/path/to/competence.pdf");
            candidat.setStatuCandidat(Candidat.StatuCandidat.SHORTLISTE);
            candidat.setDisponibilite(Candidat.Disponibilite.IMMEDIATE);

            // Ajouter la candidature
            serviceCandidat.ajouter(candidat);
            System.out.println("Candidature ajoutée avec succès !");

            // Afficher toutes les candidatures
            List<Candidat> candidats = serviceCandidat.afficher();
            System.out.println("Liste des candidatures :");
            for (Candidat c : candidats) {
                System.out.println(c);
            }



        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution des opérations : " + e.getMessage());
        }
    }
}