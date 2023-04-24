package interfaceGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;

public class AddTeamForm {

    private JPanel mainPanel;
    private Connection connection;

    //Add team form inputs and labels
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
        
        //Submit button creation + actions
        JButton submitButton = new JButton("Submit");
        //Listener for submit button
        submitButton.setBounds(10, 230, 225, 60);
        submitButton.addActionListener(new ActionListener() {
            @Override
            
            //Action for submitting team form 
            public void actionPerformed(ActionEvent e) {
                String coach = coachField.getText();
                String assistantCoach = assistantCoachField.getText();
                String teamName = teamNameField.getText();

                //Make sure that Coach and assistant coach are only letters 
                if (!isAlphaOnly(coach) || !isAlphaOnly(assistantCoach)) {
                    JOptionPane.showMessageDialog(mainPanel, "Coach and Assistant Coach names should only contain letters.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Check for duplicate team names 
                boolean isUnique = false;
                try {
                    isUnique = isTeamNameUnique(teamName);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //error message for duplicate team name only 
                if (!isUnique) {
                    JOptionPane.showMessageDialog(mainPanel, "The team name already exists. Please choose a different name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean success = false;
                try {
                    addTeamToDatabase(coach, assistantCoach, teamName, null);
                    success = true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if (success) {
                    JOptionPane.showMessageDialog(mainPanel, "Team added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Failed to add the team.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        //Set main pain back to null and then add features back to main panel 
        mainPanel.setLayout(null);
        mainPanel.add(coachLabel);
        mainPanel.add(coachField);
        mainPanel.add(assistantCoachLabel);
        mainPanel.add(assistantCoachField);
        mainPanel.add(teamNameLabel);
        mainPanel.add(teamNameField);
        //Reset
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

    //Selecting all from SoccerTeams and checking for unique name 
    private boolean isTeamNameUnique(String teamName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SoccerTeams WHERE TeamName = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teamName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            } else {
                return true;
            }
        }
    }
    
    //Adding team to database
    //Added if team name value is unique
    private void addTeamToDatabase(String coach, String assistantCoach, String teamName, String playerIdList) throws SQLException {
        String sql = "INSERT INTO SoccerTeams (TeamID, Coach, AssistantCoach, TeamName, PlayerIdList) VALUES (?, ?, ?, ?, ?)";

        //team ID is set and handled automatically 
        //Removed feature of editing team Id, to ensure database keeps track instead
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int nextTeamId = getNextTeamId();
            statement.setInt(1, nextTeamId);
            statement.setString(2, coach);
            statement.setString(3, assistantCoach);
            statement.setString(4, teamName);

            if (playerIdList == null || playerIdList.isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, playerIdList);
            }

            //checking for addition of team 
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
    //Only alphabet characters accepted and spaces
    private boolean isAlphaOnly(String input) {
        return input.matches("[a-zA-Z ]+");
    }
    
    //getting the next availible id for teams since it is not in the database as being autonumber 
    //this is because of errors arising with autonumber and deletion of teams
    private int getNextTeamId() throws SQLException {
        String sql = "SELECT MAX(TeamID) FROM SoccerTeams";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxTeamId = resultSet.getInt(1);
                return maxTeamId + 1;
            } else {
                return 1;
            }
        }
    }
}