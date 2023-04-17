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
        
        JLabel coachLabel = new JLabel("Coach:");
        coachLabel.setBounds(0, 16, 87, 27);
        JTextField coachField = new JTextField();
        coachField.setBounds(45, 20, 225, 19);
        JLabel assistantCoachLabel = new JLabel("Assistant Coach:");
        assistantCoachLabel.setBounds(0, 38, 225, 60);
        JTextField assistantCoachField = new JTextField();
        assistantCoachField.setBounds(90, 59, 180, 19);
        JLabel teamNameLabel = new JLabel("Team Name:");
        teamNameLabel.setBounds(0, 88, 225, 60);
        JTextField teamNameField = new JTextField();
        teamNameField.setBounds(91, 109, 179, 19);
        
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 230, 225, 60);
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
        mainPanel.setLayout(null);
        
        mainPanel.add(coachLabel);
        mainPanel.add(coachField);
        mainPanel.add(assistantCoachLabel);
        mainPanel.add(assistantCoachField);
        mainPanel.add(teamNameLabel);
        mainPanel.add(teamNameField);
        JLabel label = new JLabel("");
        label.setBounds(0, 180, 225, 60);
        mainPanel.add(label);
        JLabel label_1 = new JLabel("");
        label_1.setBounds(225, 180, 225, 60);
        mainPanel.add(label_1);
        JLabel label_2 = new JLabel("");
        label_2.setBounds(0, 240, 225, 60);
        mainPanel.add(label_2);
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