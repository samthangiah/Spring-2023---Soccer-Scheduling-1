package soccerManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//Deletion of selected league from league search 
public class DeleteLeagueController {
    public static void deleteLeague(String leagueName) {
        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM SoccerLeague WHERE LeagueName = ?");
            statement.setString(1, leagueName);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "League deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete league. Please try again.");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the league. Please try again.");
        }
    }
}