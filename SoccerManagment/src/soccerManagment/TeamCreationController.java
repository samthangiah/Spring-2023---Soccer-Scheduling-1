package soccerManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Creation of team for SoccerTeam table in database 
public class TeamCreationController {
	public static void assignPlayerToTeam(int playerId, int teamId, String league) {
	    Connection connection = null;
	    try {
	        connection = DatabaseConnection.openConnection();

	        // Update PlayerInformation table
	        PreparedStatement statement = connection.prepareStatement("UPDATE PlayerInformation SET TeamId = ?, League = ?, Registered = ?, Assigned = ? WHERE PlayerId = ?");
	        statement.setInt(1, teamId);
	        statement.setString(2, league);
	        statement.setBoolean(3, true);
	        statement.setBoolean(4, true);
	        statement.setInt(5, playerId);

	        statement.executeUpdate();

	        // Update TeamList column in SoccerLeague table
	        statement = connection.prepareStatement("SELECT TeamList FROM SoccerLeague WHERE LeagueName = ?");
	        statement.setString(1, league);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            String teamList = resultSet.getString("TeamList");
	            Set<String> teamIds = new HashSet<>();

	            if (teamList != null && !teamList.isEmpty()) {
	                teamIds.addAll(Arrays.asList(teamList.split(",")));
	            }

	            // Add the teamId to the set (automatically handles duplicates)
	            teamIds.add(String.valueOf(teamId));

	            // Convert the set back to a comma-separated string
	            teamList = String.join(",", teamIds);

	            statement = connection.prepareStatement("UPDATE SoccerLeague SET TeamList = ? WHERE LeagueName = ?");
	            statement.setString(1, teamList);
	            statement.setString(2, league);
	            statement.executeUpdate();
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DatabaseConnection.closeConnection(connection);
	    }
	}
}