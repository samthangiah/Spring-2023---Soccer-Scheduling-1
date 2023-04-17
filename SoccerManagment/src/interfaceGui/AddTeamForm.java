package interfaceGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;

public class AddTeamForm {

    private JPanel mainPanel;
    private Connection connection;

    public AddTeamForm() {
        connection = DatabaseConnection.openConnection();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        
        JLabel coachLabel = new JLabel("Coach:");
        JTextField coachField = new JTextField();
        JLabel assistantCoachLabel = new JLabel("Assistant Coach:");
        JTextField assistantCoachField = new JTextField();
        JLabel teamNameLabel = new JLabel("Team Name:");
        JTextField teamNameField = new JTextField();
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coach = coachField.getText();
                String assistantCoach = assistantCoachField.getText();
                String teamName = teamNameField.getText();
                
                try {
                    addTeamToDatabase(coach, assistantCoach, teamName, null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        mainPanel.add(coachLabel);
        mainPanel.add(coachField);
        mainPanel.add(assistantCoachLabel);
        mainPanel.add(assistantCoachField);
        mainPanel.add(teamNameLabel);
        mainPanel.add(teamNameField);
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));
        mainPanel.add(submitButton);
    }

    public JPanel getContentPane() {
        return mainPanel;
    }

    private void addTeamToDatabase(String coach, String assistantCoach, String teamName, String playerIdList) throws SQLException {
        String sql = "INSERT INTO SoccerTeams (Coach, AssistantCoach, TeamName, PlayerIdList) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, coach);
            statement.setString(2, assistantCoach);
            statement.setString(3, teamName);

            if (playerIdList == null || playerIdList.isEmpty()) {
                statement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                statement.setString(4, playerIdList);
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Team added successfully!");
            } else {
                System.out.println("Failed to add the team.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}