package soccerManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;

public class DeletePlayerController {
    
	public void deletePlayer(int playerId) {
	    // SQL query to delete the player from the database
	    String deleteQuery = "DELETE FROM PlayerInformation WHERE PlayerId = ?";
	    
	    // SQL query to update the PlayerId values of the remaining rows
	    String updateQuery = "UPDATE PlayerInformation SET PlayerId = PlayerId - 1 WHERE PlayerId > ?";
	    
	    Connection connection = null;
	    PreparedStatement deleteStatement = null;
	    PreparedStatement updateStatement = null;

	    try {
	        connection = DatabaseConnection.openConnection();
	        // Prepare the delete statement
	        deleteStatement = connection.prepareStatement(deleteQuery);
	        deleteStatement.setInt(1, playerId);
	        deleteStatement.executeUpdate();

	        // Prepare the update statement
	        updateStatement = connection.prepareStatement(updateQuery);
	        updateStatement.setInt(1, playerId);
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