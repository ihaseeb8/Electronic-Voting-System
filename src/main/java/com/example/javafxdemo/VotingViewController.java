package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VotingViewController {

    @FXML
    private Button castVoteButton;

    @FXML
    private Text castVotePrompt;

    @FXML
    private Button voterBackButton;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {

        DBConnection dbConnection = DBConnection.getDBConnection();

        ObservableList<String> parties = dbConnection.getParties();

        comboBox.setItems(parties);

    }

    @FXML
    void castVoteButtonPressed(ActionEvent event) throws IOException {

        Voter voter = Voter.getVoter();

        if(comboBox.getSelectionModel().isEmpty())
        {
            castVotePrompt.setText("No Party Selected!");
        }
        else
        {
            castVotePrompt.setText("");

            String temp = "";
            String party = comboBox.getValue();
            int k =0;
            while(party.charAt(k) != '(')
            {
                temp += party.charAt(k);
                k++;
            }

            voter.voterParty = temp;

            DBConnection dbConnection = DBConnection.getDBConnection();
            dbConnection.insertVoter(voter.voterCnic,voter.voterParty);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FinalView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                //Node node = (Node) event.getSource();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Elections 2021");
                stage.show();
            }
            catch (Exception e)
            {
                System.out.println(e.getStackTrace()+ "---" + e.getCause());
            }
        }
    }

    @FXML
    void comboBoxPressed(ActionEvent event) {

    }

    @FXML
    void voterBackButtonPressed(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Node node = (Node) event.getSource();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Elections 2021");
        stage.show();
    }
}
