package interfaceGui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import soccerManagment.DatabaseConnection;

public class ResetDatabase {

    public static void resetPlayerStatus() {
        Connection connection = null;
        try {
            // Open connection to the database
            connection = DatabaseConnection.openConnection();

            if (connection == null) {
                System.out.println("Error: No connection to database.");
                return;
            }

            System.out.println("Connection established.");

            // Update player status in the PlayerInformation table
            //Deal with registered assigned teamId and League name for players when database is to be reset 
            String playerSql = "UPDATE PlayerInformation SET Registered = ?, Assigned = ?, TeamID = ?, League = ?";
            PreparedStatement playerStatement = connection.prepareStatement(playerSql);
            playerStatement.setBoolean(1, false);
            playerStatement.setBoolean(2, false);
            playerStatement.setInt(3, 0);
            playerStatement.setString(4, "NONE");
            int playerRowsAffected = playerStatement.executeUpdate();
            System.out.println("Updated " + playerRowsAffected + " row(s) in the PlayerInformation table.");

            playerStatement.close();

            // Update TeamList column in the SoccerLeague table
            String soccerLeagueSql = "UPDATE SoccerLeague SET TeamList = ?";
            PreparedStatement soccerLeagueStatement = connection.prepareStatement(soccerLeagueSql);
            soccerLeagueStatement.setString(1, "");
            int soccerLeagueRowsAffected = soccerLeagueStatement.executeUpdate();
            System.out.println("Updated " + soccerLeagueRowsAffected + " row(s) in the SoccerLeague table.");

            soccerLeagueStatement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close the connection
            DatabaseConnection.closeConnection(connection);
        }
    }
}