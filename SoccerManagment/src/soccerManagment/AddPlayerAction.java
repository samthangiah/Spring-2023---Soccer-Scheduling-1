package soccerManagment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPlayerAction {
    
    public static void addPlayer(int playerId, String firstName, String lastName, Date birthdate, String gender, int skillLevel, int seasonsPlayed, boolean isRegistered, boolean isAssigned) {
        Connection connection = null;
        try {
            // Open connection to the database
            connection = DatabaseConnection.openConnection();

            if (connection == null) {
                System.out.println("Error: No connection to database.");
                return;
            }

            System.out.println("Connection established.");
            
            // Insert player information into the PlayerInformation table
            String sql = "INSERT INTO PlayerInformation (PlayerId, FirstName, LastName, Birthdate, Gender, SkillLevel, SeasonsPlayed, Registered, Assigned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setDate(4, birthdate);
            preparedStatement.setString(5, gender);
            preparedStatement.setInt(6, skillLevel);
            preparedStatement.setInt(7, seasonsPlayed);
            preparedStatement.setBoolean(8, isRegistered);
            preparedStatement.setBoolean(9, isAssigned);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into the PlayerInformation table.");

            preparedStatement.close();

        } catch (SQLException ex) {
            System.out.println("Error: Failed to add player. Reason: " + ex.getMessage());
        } finally {
            // Close the connection
            DatabaseConnection.closeConnection(connection);
        }
    }
}