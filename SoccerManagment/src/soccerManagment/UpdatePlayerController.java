package soccerManagment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import soccerManagment.DatabaseConnection;

public class UpdatePlayerController {

    public static void updatePlayerData(int playerId, String firstName, String lastName, String gender, String birthdate, int skillLevel, String seasonsPlayed, String assigned, String registered, String address, String city, String state, int zipCode, int carPool, String league, String jerseySize, String shortSize, String sockSize, String paid, String medicalInsurer, String medicalConcerns, String adultLastName, String adultFirstName, String adultPhone1, String adultPhone2, String adultEmail, String secondAdultLastName, String secondAdultFirstName, String secondAdultPhone1, String secondAdultPhone2, String secondAdultEmail, int teamId) {
        Connection connection = null;
        PreparedStatement statement = null;

        // Call the validatePlayerData method to check for errors
        List<String> errors = PlayerValidationController.validatePlayerData(getAllTeamIds(), firstName, lastName, Date.valueOf(birthdate), gender, skillLevel, Integer.parseInt(seasonsPlayed), Boolean.parseBoolean(registered), Boolean.parseBoolean(assigned), address, city, state, zipCode, carPool, league, jerseySize, shortSize, sockSize, paid, medicalInsurer, medicalConcerns, adultLastName, adultFirstName, adultPhone1, adultPhone2, adultEmail, secondAdultLastName, secondAdultFirstName, secondAdultPhone1, secondAdultPhone2, secondAdultEmail, teamId);

        // If no errors are found, execute the update operation
        if (errors.isEmpty()) {
            try {
                connection = DatabaseConnection.openConnection();
                statement = connection.prepareStatement("UPDATE PlayerInformation SET FirstName=?, LastName=?, Gender=?, Birthdate=?, SkillLevel=?, SeasonsPlayed=?, Assigned=?, Registered=?, Address=?, City=?, State=?, ZipCode=?, CarPool=?, League=?, JerseySize=?, ShortSize=?, SockSize=?, Paid=?, MedicalInsurance=?, MedicalConcerns=?, AdultLastName=?, AdultFirstName=?, AdultPhone1=?, AdultPhone2=?, AdultEmail=?, SecondAdultLastName=?, SecondAdultFirstName=?, SecondAdultPhone1=?, SecondAdultPhone2=?, SecondAdultEmail=?, TeamId=? WHERE PlayerId=?");
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, gender);
                statement.setString(4, birthdate);
                statement.setInt(5, skillLevel);
                statement.setString(6, seasonsPlayed);
                statement.setString(7, assigned);
                statement.setString(8, registered);
                statement.setString(9, address);
                statement.setString(10, city);
                statement.setString(11, state);
                statement.setInt(12, zipCode);
                statement.setInt(13, carPool);
                statement.setString(14, league);
                statement.setString(15, jerseySize);
                statement.setString(16, shortSize);
                statement.setString(17, sockSize);
                statement.setString(18, paid);
                statement.setString(19, medicalInsurer);
                statement.setString(20, medicalConcerns);
                statement.setString(21, adultLastName);
                statement.setString(22, adultFirstName);
                statement.setString(23, adultPhone1);
                statement.setString(24, adultPhone2);
                statement.setString(25, adultEmail);
                statement.setString(26, secondAdultLastName);
                statement.setString(27, secondAdultFirstName);
                statement.setString(28, secondAdultPhone1);
                statement.setString(29, secondAdultPhone2);
                statement.setString(30, secondAdultEmail);
                statement.setInt(31, teamId);
                statement.setInt(32, playerId);

                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Player information updated successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // If errors are found, print the error messages
        	StringBuilder errorMessages = new StringBuilder();
        	for (String error : errors) {
        	    errorMessages.append(error).append("\n");
        	}
        	JOptionPane.showMessageDialog(null, errorMessages.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Set<Integer> getAllTeamIds() {
        Set<Integer> teamIds = new HashSet<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.openConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT TeamId FROM SoccerTeams");

            while (resultSet.next()) {
                teamIds.add(resultSet.getInt("TeamId"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return teamIds;
    }
}