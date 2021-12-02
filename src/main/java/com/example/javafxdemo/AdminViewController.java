package com.example.javafxdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminViewController {

    @FXML
    private Button addPartyButton;

    @FXML
    private Button adminBackButton;

    @FXML
    private Text adminPagePrompt;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private TextField candidateNameTextField;

    @FXML
    private TextField partyNameTextField;

    @FXML
    void addPartyButtonPressed(ActionEvent event) {

        if(candidateNameTextField.getText().isEmpty() || partyNameTextField.getText().isEmpty())
        {
            adminPagePrompt.setText("Missing Party/Candidate Name!");
        }
        else
        {
            if(adminPasswordField.getText().isEmpty())
            {
                adminPagePrompt.setText("Missing Password!");
            }
            else
            {
                if(!Objects.equals(adminPasswordField.getText(), "tiger"))
                {
                    adminPagePrompt.setText("Invalid Password!");
                }
                else
                {
                    String partyName = partyNameTextField.getText();
                    String candidateName = candidateNameTextField.getText();

                    Party party1 = new Party(partyName,candidateName);

                    DBConnection dbConnection = DBConnection.getDBConnection();

                    if( dbConnection.searchParty(partyName) == true )
                    {
                        adminPagePrompt.setText("Party Already Exists!");
                    }
                    else
                    {
                        dbConnection.insertParty(party1);
                        adminPagePrompt.setText("Party Added To Database!");

                    }
                }
            }
        }

    }

    @FXML
    void adminBackButtonPressed(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Node node = (Node) event.getSource();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Elections 2021");
        stage.show();

    }
}
