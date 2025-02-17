package tn.esprit.formations.test;

import tn.esprit.formations.interfaces.FormationApp;
import tn.esprit.formations.utils.MyDatabase;

public class Main {
    public static void main(String[] args){
        MyDatabase db = MyDatabase.getInstance();
        FormationApp formationApp = new FormationApp();
    }
}
