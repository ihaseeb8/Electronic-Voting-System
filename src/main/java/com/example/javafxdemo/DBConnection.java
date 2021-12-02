package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Objects;

import static java.lang.Integer.parseInt;

class DBConnection {

    private static DBConnection obj=new DBConnection();

    private DBConnection(){

    }

    public static DBConnection getDBConnection(){
        return obj;
    }

    public boolean searchVoter(String cnic){

        try
        {
                //step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver Loaded Successfully!");

                //step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mysql","root","tiger");

            System.out.println("Connection Established!");

            String sql = "SELECT cnic FROM ap.voters WHERE cnic = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, cnic);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {

                String cnicFetched = rs.getString("cnic"); // by column name matchin

                if( parseInt(cnicFetched ) == parseInt(cnic) ) {
                       return true;
                }

            }

            con.close();

        }
        catch(ClassNotFoundException e){

            System.out.println("Driver Not Loaded");
            return false;

        } catch (SQLException e) {

            System.out.println("Connection is not Established!" + e.getMessage());
            return false;
        }

        return  false;
    }

    public boolean insertVoter(String cnic, String party){

        try
        {
            //step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver Loaded Successfully!");

            //step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mysql","root","tiger");

            System.out.println("Connection Established!");

            String sql = "INSERT INTO ap.voters(cnic,party_name) VALUES (? , ?) ";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, cnic);
            statement.setString(2, party);
            int rowIns = statement.executeUpdate();

            //Now incrementing votes of Party
            sql = "SELECT party_name , total_votes FROM ap.parties WHERE party_name = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, party);
            ResultSet rs = statement.executeQuery();

            int voteCount=0;

            while(rs.next())
            {
                voteCount = rs.getInt("total_votes"); // by column name matchin
                //System.out.println(voteCount);
                voteCount++;
                break;
            }

            //updating query
            sql = "UPDATE ap.parties SET total_votes = ? WHERE party_name = ?";
            statement = con.prepareStatement(sql);

            statement.setString(1, String.valueOf(voteCount));
            statement.setString(2, party);
            statement.executeUpdate();


            con.close();

            if(rowIns > 0)
                return true;
            else
                return false;


        }
        catch(ClassNotFoundException e){

            System.out.println("Driver Not Loaded");
            return false;

        } catch (SQLException e) {

            System.out.println("Connection is not Established!" + e.getMessage());
            return false;
        }

    }

    public boolean searchParty(String partyName){

        try
        {
            //step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver Loaded Successfully!");

            //step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mysql","root","tiger");

            System.out.println("Connection Established!");

            String sql = "SELECT party_name FROM ap.parties WHERE party_name = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, partyName);

            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {

                String partyFetched = rs.getString("party_name"); // by column name matchin

                if(Objects.equals(partyFetched, partyName)) {
                    return true;
                }

            }

            con.close();

        }
        catch(ClassNotFoundException e){

            System.out.println("Driver Not Loaded");
            return false;

        } catch (SQLException e) {

            System.out.println("Connection is not Established!" + e.getMessage());
            return false;
        }

        return  false;
    }

    public boolean insertParty(Party party1){

        try
        {
            //step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver Loaded Successfully!");

            //step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mysql","root","tiger");

            System.out.println("Connection Established!");

            String sql = "INSERT INTO ap.parties(party_name , candidate_name) VALUES (? , ?) ";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, party1.partyName);
            statement.setString(2, party1.candidateName);

            int rowIns = statement.executeUpdate();

            con.close();

            if(rowIns > 0)
                return true;
            else
                return false;


        }
        catch(ClassNotFoundException e){

            System.out.println("Driver Not Loaded");
            return false;

        } catch (SQLException e) {

            System.out.println("Connection is not Established!" + e.getMessage());
            return false;
        }

    }

    public ObservableList<String> getParties() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Driver Loaded Successfully!");

        //step2 create  the connection object
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mysql","root","tiger");

        System.out.println("Connection Established!");

        String sql = "SELECT party_name,candidate_name FROM ap.parties ";

        PreparedStatement statement = con.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        ObservableList<String> parties = FXCollections.observableArrayList();

        while(rs.next())
        {
            String partyFetched = rs.getString("party_name"); // by column name matchin
            String candidateFetched = rs.getString("candidate_name");

            partyFetched = partyFetched + "( " + candidateFetched + " )";

           // System.out.println(partyFetched);

            parties.add(partyFetched);
        }

        con.close();

        return parties;
    }
}

