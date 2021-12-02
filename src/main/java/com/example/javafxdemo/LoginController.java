package com.example.javafxdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class LoginController {

    @FXML
    public Text loginPrompt;

    @FXML
    private TextField cnicTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button adminButton;

    @FXML
    void loginButtonPressed(ActionEvent event) {
        String cnic = cnicTextField.getText();

        try {
            int cnicInt = parseInt(cnic);

            if (cnicInt < 100000000 || cnicInt > 199999999) {
                loginPrompt.setText("Invalid CNIC!");
            }
            else
            {
                DBConnection dbConnection = DBConnection.getDBConnection();

                if(dbConnection.searchVoter(cnic) == true)
                {
                    loginPrompt.setText("Vote Already Casted!");
                }
                else
                {

                    Voter voter = Voter.getVoter();
                    voter.setVoter(cnic,"");
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("VotingView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    //Node node = (Node) event.getSource();
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Elections 2021");
                    stage.show();

                }

            }

        }
        catch(Exception e)
        {
            System.out.println(e.getCause());
        }

    }

    @FXML
    void adminButtonPressed(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Node node = (Node) event.getSource();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Elections 2021");
        stage.show();

    }

}
