package tn.esprit.formations.entities;

import tn.esprit.formations.entities.CompetencesCibles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Formation {

    int id;
    private String titre;
    private String description;
    private List<Module> modules = new ArrayList<>();
    private Set<Inscription> Inscriptions = new HashSet<>();
    private List<CompetencesCibles> competencesCibles = new ArrayList<>();
    private int idQuizFinal;

    // Constructeurs

    public Formation(int id, String titre, String description, List<Module> modules, Set<Inscription> inscriptions, List<CompetencesCibles> competencesCibles, int idQuizFinal) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.modules = modules;
        Inscriptions = inscriptions;
        this.competencesCibles = competencesCibles;
        this.idQuizFinal = idQuizFinal;
    }

    public Formation() {
        
    }

    public Formation(String titreValue, String description) {
    }

    // Getters/Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Set<Inscription> getInscriptions() {
        return Inscriptions;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        Inscriptions = inscriptions;
    }

    public List<CompetencesCibles> getCompetencesCibles() {
        return competencesCibles;
    }

    public void setCompetencesCibles(List<CompetencesCibles> competencesCibles) {
        this.competencesCibles = competencesCibles;
    }

    public int getIdQuizFinal() {
        return idQuizFinal;
    }

    public void setIdQuizFinal(int idQuizFinal) {
        this.idQuizFinal = idQuizFinal;
    }
}
