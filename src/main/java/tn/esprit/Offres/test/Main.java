package tn.esprit.Offres.test;

import tn.esprit.Offres.entities.Candidat;
import tn.esprit.Offres.entities.Candidature;
import tn.esprit.Offres.entities.Offre;
import tn.esprit.Offres.services.ServiceCandidat;
import tn.esprit.Offres.entities.User;
import tn.esprit.Offres.services.ServiceCandidature;
import tn.esprit.Offres.services.ServiceOffres;
import tn.esprit.Offres.services.ServiceUser;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialiser les services
        ServiceUser serviceUser = new ServiceUser();
        ServiceCandidat serviceCandidat = new ServiceCandidat();
        ServiceOffres serviceOffres = new ServiceOffres();
        ServiceCandidature serviceCandidature = new ServiceCandidature();
        Candidature candidature = new Candidature();
        candidature.setIdCandidat(13); // ID du candidat existant
        candidature.setIdOffre(4); // ID de l'offre existante
        candidature.setDateCandidature(LocalDate.now()); // Date de candidature (aujourd'hui)
        candidature.setStatutCandidature(Candidature.StatutCandidature.EN_ATTENTE); // Statut de la candidature
        candidature.setNoteCandidat(85); // Note du candidat
        candidature.setCommentaires("Très bon profil technique"); // Commentaires
        candidature.setDateEntretien(LocalDate.now().plusDays(7)); // Date de l'entretien (dans 7 jours)
        candidature.setResultatEntretien(Candidature.ResultatEntretien.EN_ATTENTE); // Résultat de l'entretien
        candidature.setEtapeActuelle(Candidature.EtapeCandidature.ENTRETIEN_TECHNIQUE); // Étape actuelle
        candidature.setDateMiseAJourStatut(LocalDate.now()); // Date de mise à jour du statut
        candidature.setRecruteurResponsable("Recruteur1"); // Recruteur responsable

        try {
            // Créer un nouvel utilisateur
           /* User user = new User();
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
            }*/

            /*Offre nouvelleOffre = new Offre(
                    0, // L'ID est auto-généré par la base de données
                    "Développeur Java",
                    "Développement d'applications Java",
                    new Date(System.currentTimeMillis()), // Date de publication (aujourd'hui)
                    Date.valueOf("2024-12-01"), // Date d'acceptation (non définie)
                    30, // Time to Hire (en jours)
                    45, // Time to Fill (en jours)
                    "Ouverte", // Statut de l'offre
                    "Informatique", // Département
                    "Recruteur1" // Recruteur responsable
            );
            serviceOffres.ajouter(nouvelleOffre);
            System.out.println("Offre ajoutée avec succès !");*/
            /*List<Offre> offres = serviceOffres.afficher();

            // Vérification si des offres ont été récupérées et affichage
            if (offres.isEmpty()) {
                System.out.println("Aucune offre n'est disponible.");
            } else {
                // Affichage des offres
                for (Offre offre : offres) {
                    System.out.println(offre);  // Utilisation de la méthode toString() de la classe Offre
                }
            }*/

            /*
            List<Offre> offresAvantModification = serviceOffres.afficher();
            System.out.println("Offres avant modification : ");
            for (Offre offre : offresAvantModification) {
                System.out.println(offre);
            }

            // Supposons que tu veux modifier l'offre avec un ID spécifique, par exemple, idOffre = 1
            int idOffreAModifier = 1;
            Offre offreAModifier = null;

            // On cherche l'offre par son ID (ici, l'offre avec l'ID 1)
            for (Offre offre : offresAvantModification) {
                if (offre.getIdOffre() == idOffreAModifier) {
                    offreAModifier = offre;
                    break;
                }
            }

            if (offreAModifier != null) {
                // Modification des détails de l'offre
                offreAModifier.setTitrePoste("Développeur Backend Java");
                offreAModifier.setDescription("Développement d'applications Backend Java");
                offreAModifier.setStatuOffre("Fermée");

                // Appel de la méthode modifier() pour appliquer la modification
                serviceOffres.modifier(offreAModifier);

                System.out.println("Offre après modification : ");
                System.out.println(offreAModifier); // Affichage de l'offre modifiée
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }

            // Affichage final des offres après modification
            System.out.println("\nOffres après modification : ");
            List<Offre> offresApresModification = serviceOffres.afficher();
            for (Offre offre : offresApresModification) {
                System.out.println(offre);
            }*/
            serviceCandidature.ajouter(candidature);
            System.out.println("Candidature ajoutée avec succès !");

        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'ajout de la candidature : " + e.getMessage());
        }
    }
}