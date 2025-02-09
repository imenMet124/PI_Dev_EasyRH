package tn.esprit.formations.entities;

import tn.esprit.formations.CompetencesCibles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Formation {
    private int id;
    private String titre;
    private String description;
    private List<Module> modules = new ArrayList<>();
    private Set<Inscription> Inscriptions = new HashSet<>();
    private CompetencesCibles competencesCibles;
    private Quiz quizFinal;

    // Constructeurs
    public Formation() {}

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

    public CompetencesCibles getCompetencesCibles() {
        return competencesCibles;
    }

    public void setCompetencesCibles(CompetencesCibles competencesCibles) {
        this.competencesCibles = competencesCibles;
    }

    public Quiz getQuizFinal() {
        return quizFinal;
    }

    public void setQuizFinal(Quiz quizFinal) {
        this.quizFinal = quizFinal;
    }
}
