package tn.esprit.formations.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AjoutFormationController {
    @FXML
    private TextField titreField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ListView<String> competencesListView;
    @FXML
    private ListView<String> modulesListView;
    @FXML
    private ComboBox<String> quizComboBox;
    @FXML
    private Button ajouterFormationButton;




}

