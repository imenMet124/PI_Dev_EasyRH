package tn.esprit.Offres.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DetailsController {

    @FXML
    private TextField resnom;

    @FXML
    private TextField resprenom;
public void setResnom(String resnom){
    this.resnom.setText(resnom);
}
    public void setResprenom(String resprenom){
        this.resprenom.setText(resprenom);
    }
}

