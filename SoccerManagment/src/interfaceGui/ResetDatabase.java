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
            String sql = "UPDATE PlayerInformation SET Registered = ?, Assigned = ?, TeamID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, 0);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s) in the PlayerInformation table.");

            preparedStatement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close the connection
            DatabaseConnection.closeConnection(connection);
        }
    }

}