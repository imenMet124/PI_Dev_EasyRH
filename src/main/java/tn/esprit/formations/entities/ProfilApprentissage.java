package tn.esprit.formations.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilApprentissage {
    private int id;
    private int idUtilisateur;
    private Map<String, Integer> competences = new HashMap<>(); // <Compétence, Niveau>
    private List<Certification> certifications = new ArrayList<>();

    public void ajouterCertification(Certification certif) {
        certifications.add(certif);
    }
}
