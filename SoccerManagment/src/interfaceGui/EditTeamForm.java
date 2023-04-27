package interfaceGui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import soccerManagment.DatabaseConnection;

//Form for editing teams in database
public class EditTeamForm extends JPanel {

    private JTextField teamNameField;
    private JTextField coachField;
    private JTextField assistantCoachField;
    private int teamId;
    private Connection connection;
    private JPanel previousPanel;

    public EditTeamForm(int teamId, JPanel previousPanel) {
        this.teamId = teamId;
        this.previousPanel = previousPanel;
        setLayout(new BorderLayout());
        // Connect to the database
        connection = DatabaseConnection.openConnection();

        // Fetch the team information from the database
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerTeams WHERE TeamID = ?");
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String teamName = resultSet.getString("TeamName");
                String coach = resultSet.getString("Coach");
                String assistantCoach = resultSet.getString("AssistantCoach");

                // Populate the text fields in the form
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(3, 2));
                add(inputPanel, BorderLayout.CENTER);

                JLabel teamNameLabel = new JLabel("Team Name:");
                inputPanel.add(teamNameLabel);
                teamNameField = new JTextField(teamName);
                inputPanel.add(teamNameField);

                JLabel coachLabel = new JLabel("Coach:");
                inputPanel.add(coachLabel);
                coachField = new JTextField(coach);
                inputPanel.add(coachField);

                JLabel assistantCoachLabel = new JLabel("Assistant Coach:");
                inputPanel.add(assistantCoachLabel);
                assistantCoachField = new JTextField(assistantCoach);
                inputPanel.add(assistantCoachField);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching team information");
        }

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        add(buttonsPanel, BorderLayout.SOUTH);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousPanel.removeAll();
                previousPanel.add(new TeamSearch(), BorderLayout.CENTER);
                previousPanel.revalidate();
                previousPanel.repaint();
            }
        });
        buttonsPanel.add(backButton);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeam();
            }
        });
        buttonsPanel.add(submitButton);
    }

    //called for updates 
    private void updateTeam() {
        try {
            String sql = "UPDATE SoccerTeams SET TeamName=?, Coach=?, AssistantCoach=? WHERE TeamID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamNameField.getText());
            statement.setString(2, coachField.getText());
            statement.setString(3, assistantCoachField.getText());
            statement.setInt(4, teamId);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Team updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating the team");
        }
    }
}