package interfaceGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import soccerManagment.DatabaseConnection;
import interfaceGui.AddTeamForm;

public class TeamWindow extends JFrame {

    private Connection connection;
    private JTable soccerTeamsTable;
    private JScrollPane soccerTeamsTableScrollPane;
    private DefaultTableModel soccerTeamsTableModel;

    public TeamWindow() {
        setTitle("Team Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create left panel with buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        getContentPane().add(leftPanel, BorderLayout.WEST);

        JButton btnTab1 = new JButton("Add Team");
        leftPanel.add(btnTab1);

        JButton btnTab2 = new JButton("Edit/Delete");
        leftPanel.add(btnTab2);

        JButton btnOriginalTab = new JButton("Team List");
        leftPanel.add(btnOriginalTab);

        // Create right panel with contents
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        // Create new card content for Tab 1
        JPanel tab1Content = new JPanel();

        // Create new content for Original Tab
        JPanel originalTabContent = new JPanel();
        originalTabContent.add(new JLabel("Original Content"));

        soccerTeamsTableModel = new DefaultTableModel(new String[]{"TeamID", "Coach", "AssistantCoach", "TeamName", "PlayerIdList"}, 0);
        soccerTeamsTable = new JTable(soccerTeamsTableModel);
        soccerTeamsTableScrollPane = new JScrollPane(soccerTeamsTable);
        rightPanel.add(soccerTeamsTableScrollPane, BorderLayout.CENTER);

        btnTab1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rightPanel.remove(soccerTeamsTableScrollPane);
                AddTeamForm addTeamForm = new AddTeamForm();
                JPanel tab1Content = addTeamForm.getContentPane();
                rightPanel.add(tab1Content, BorderLayout.CENTER);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        btnTab2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform action for Tab 2
            }
        });

        btnOriginalTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Original Tab button clicked");
                rightPanel.removeAll();
                rightPanel.add(soccerTeamsTableScrollPane, BorderLayout.CENTER);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        // Connect to the database
        connection = DatabaseConnection.openConnection();
        if (connection != null) {
            try {
                displaySoccerTeams(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseConnection.closeConnection(connection);
            }
        });
    }
    public Set<Integer> getTeamIds() {
        Set<Integer> teamIds = new HashSet<>();
        int rowCount = soccerTeamsTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            teamIds.add((Integer) soccerTeamsTableModel.getValueAt(i, 0));
        }

        return teamIds;
    }
    
    private void displaySoccerTeams(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT * FROM SoccerTeams";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int teamId = resultSet.getInt("TeamID"); // Fetch the TeamID column value
                String coach = resultSet.getString("Coach");
                String assistantCoach = resultSet.getString("AssistantCoach");
                String teamName = resultSet.getString("TeamName");

                // Fetch the PlayerIDs with the same Team ID
                PreparedStatement playerStatement = connection.prepareStatement("SELECT PlayerId FROM PlayerInformation WHERE TeamId = ?");
                playerStatement.setInt(1, teamId);
                ResultSet playerResultSet = playerStatement.executeQuery();

                StringBuilder playerIdListBuilder = new StringBuilder();
                while (playerResultSet.next()) {
                    int playerId = playerResultSet.getInt("PlayerId");
                    playerIdListBuilder.append(playerId).append(", ");
                }
                String playerIdList = playerIdListBuilder.toString().replaceAll(", $", ""); // Remove the trailing comma and space

                soccerTeamsTableModel.addRow(new Object[]{teamId, coach, assistantCoach, teamName, playerIdList}); // Include teamId in the row data

                // Close the playerStatement and playerResultSet
                playerResultSet.close();
                playerStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}