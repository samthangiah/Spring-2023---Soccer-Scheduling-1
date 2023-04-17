package soccerManagment;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTeamController {

    public void deleteTeam(int teamId) {
        // SQL query to delete the team from the database
        String deleteQuery = "DELETE FROM SoccerTeams WHERE TeamID = ?";

        // SQL query to update the TeamID values of the remaining rows
        String updateQuery = "UPDATE SoccerTeams SET TeamID = TeamID - 1 WHERE TeamID > ?";

        Connection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseConnection.openConnection();

            // Prepare the delete statement
            deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, teamId);
            deleteStatement.executeUpdate();

            // Prepare the update statement
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, teamId);
            updateStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (deleteStatement != null) {
                    deleteStatement.close();
                }
                if (updateStatement != null) {
                    updateStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}