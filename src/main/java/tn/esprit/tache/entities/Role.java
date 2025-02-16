package tn.esprit.tache.entities;

public class Role {
    private int id;
    private String nomRole;

    public Role(int id, String nomRole) {
        this.id = id;
        this.nomRole = nomRole;
    }

    public int getId() { return id; }
    public String getNomRole() { return nomRole; }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", nomRole='" + nomRole + "'}";
    }
}
