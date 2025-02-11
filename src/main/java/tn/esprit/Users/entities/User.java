package tn.esprit.Users.entities;

import java.util.Date;

public class User {
    private int idEmp;
    private String nomEmp;
    private String email;
    private String phone;
    private String role;
    private String position;
    private double salaire;
    private Date dateEmbauche;
    private String statutEmp;
    private int idDep;

    // Constructor
    public User(int idEmp, String nomEmp, String email, String phone, String role, String position, double salaire, Date dateEmbauche, String statutEmp, int idDep) {
        this.idEmp = idEmp;
        this.nomEmp = nomEmp;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.position = position;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.statutEmp = statutEmp;
        this.idDep = idDep;
    }

    // Getters and Setters
    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getStatutEmp() {
        return statutEmp;
    }

    public void setStatutEmp(String statutEmp) {
        this.statutEmp = statutEmp;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "idEmp=" + idEmp +
                ", nomEmp='" + nomEmp + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", position='" + position + '\'' +
                ", salaire=" + salaire +
                ", dateEmbauche=" + dateEmbauche +
                ", statutEmp='" + statutEmp + '\'' +
                ", idDep=" + idDep +
                '}';
    }
}