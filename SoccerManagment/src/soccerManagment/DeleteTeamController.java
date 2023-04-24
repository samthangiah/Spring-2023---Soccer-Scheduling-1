package soccerManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Deletion of selected team from team search 
public class DeleteTeamController {

    public static void deleteTeam(int teamId) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement updatePlayerStatement = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseConnection.openConnection();

            // Update the TeamId of all players in the team to 0 (unassigned)
            updatePlayerStatement = connection.prepareStatement("UPDATE PlayerInformation SET TeamId = 0 WHERE TeamId = ?");
            updatePlayerStatement.setInt(1, teamId);
            updatePlayerStatement.executeUpdate();

            // Delete the team with the specified TeamId
            deleteStatement = connection.prepareStatement("DELETE FROM SoccerTeams WHERE TeamID = ?");
            deleteStatement.setInt(1, teamId);
            deleteStatement.executeUpdate();

            // Update the TeamId values of the remaining rows
            updateStatement = connection.prepareStatement("UPDATE SoccerTeams SET TeamId = TeamId - 1 WHERE TeamId > ?");
            updateStatement.setInt(1, teamId);
            updateStatement.executeUpdate();

        } finally {
            if (deleteStatement != null) {
                deleteStatement.close();
            }
            if (updatePlayerStatement != null) {
                updatePlayerStatement.close();
            }
            if (updateStatement != null) {
                updateStatement.close();
            }
            if (connection != null) {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }
}