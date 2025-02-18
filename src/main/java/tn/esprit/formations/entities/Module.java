package tn.esprit.formations.entities;

public class Module {
    private int id;
    private String titre;
    private String Description;
    private String contenu; // chemin vers fichier
    private int idQuizModule;

    //Constructeur
    public Module() {}

    public Module(int id, String titre,String Description, String contenu, int quizModule) {
        this.id = id;
        this.titre = titre;
        this.Description = Description;
        this.contenu = contenu;
        this.idQuizModule = quizModule;
    }
    // Getters/Setters
    public int getIdQuizModule() {
        return idQuizModule;
    }

    public void setIdQuizModule(int quiz) {
        this.idQuizModule = quiz;
    }

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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
