package tn.esprit.formations.test;

import tn.esprit.formations.entities.Formation;
import tn.esprit.formations.services.ServiceFormation;
import tn.esprit.formations.utils.MyDatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        ServiceFormation serviceFormation = new ServiceFormation();
        try{
            serviceFormation.afficher();
            System.out.println("affiche");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }


}
