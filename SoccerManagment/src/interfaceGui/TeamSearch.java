package interfaceGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;
import soccerManagment.DeleteTeamController;

//Searching for team
public class TeamSearch extends JPanel {

    private Connection connection;
    private JTextField searchField;
    private JTable searchResultsTable;
    private JScrollPane searchResultsScrollPane;
    private DefaultTableModel searchResultsTableModel;
    private JButton editButton;
    private JButton deleteButton;
    

    public TeamSearch() {
        setLayout(new BorderLayout());

        // Create top panel with search field and button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        JLabel searchLabel = new JLabel("Search by Team Name:");
        topPanel.add(searchLabel);

        searchField = new JTextField(20);
        topPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);

        editButton = new JButton("Edit");
        editButton.setEnabled(false);
        topPanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        topPanel.add(deleteButton);

        // Create center panel with search results table
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        searchResultsTableModel = new DefaultTableModel(new String[]{"TeamID", "Coach", "AssistantCoach", "TeamName", "PlayerIdList"}, 0);
        searchResultsTable = new JTable(searchResultsTableModel);
        searchResultsScrollPane = new JScrollPane(searchResultsTable);
        centerPanel.add(searchResultsScrollPane, BorderLayout.CENTER);

        // Connect to the database
        connection = DatabaseConnection.openConnection();

        //SearchBtn
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchQuery = searchField.getText();
                    searchTeamsByName(connection, searchQuery);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //SearchTbleResults
        //Edit delete button activation 
        searchResultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (searchResultsTable.getSelectedRow() != -1) {
                        editButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    } else {
                        editButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
                }
            }
        });

        //Edit button actions
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = searchResultsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int teamId = (int) searchResultsTableModel.getValueAt(selectedRow, 0);
                    EditTeamForm editTeamForm = new EditTeamForm(teamId, centerPanel);
                    centerPanel.removeAll();
                    centerPanel.add(editTeamForm, BorderLayout.CENTER);
                    // Hide the top panel with the search bar for editing
                    topPanel.setVisible(false);
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });

        //Delete button actions
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = searchResultsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int teamId = (int) searchResultsTableModel.getValueAt(selectedRow, 0);
                    try {
                        DeleteTeamController.deleteTeam(teamId);
                        searchTeamsByName(connection, searchField.getText());
                        JOptionPane.showMessageDialog(TeamSearch.this, "Team deleted successfully");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(TeamSearch.this, "Error deleting team");
                    }
                }
            }
        });
    }

    //Searching for teams by name
    public void searchTeamsByName(Connection connection, String teamName) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
        	String sql = "SELECT * FROM SoccerTeams WHERE TeamName LIKE ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + teamName + "%");
            resultSet = statement.executeQuery();

            // Clear the search results table
            searchResultsTableModel.setRowCount(0);

            while (resultSet.next()) {
            	// Fetch the TeamID column value 
                int teamId = resultSet.getInt("TeamID"); 
                String coach = resultSet.getString("Coach");
                String assistantCoach = resultSet.getString("AssistantCoach");
                String teamNameResult = resultSet.getString("TeamName");

                // Fetch the PlayerIDs with the same Team ID
                PreparedStatement playerStatement = connection.prepareStatement("SELECT PlayerId FROM PlayerInformation WHERE TeamId = ?");
                playerStatement.setInt(1, teamId);
                ResultSet playerResultSet = playerStatement.executeQuery();

                StringBuilder playerIdListBuilder = new StringBuilder();
                while (playerResultSet.next()) {
                    int playerId = playerResultSet.getInt("PlayerId");
                    playerIdListBuilder.append(playerId).append(", ");
                }
                // Remove the trailing comma and space
                String playerIdList = playerIdListBuilder.toString().replaceAll(", $", ""); 
                // Include teamId in the row data
                searchResultsTableModel.addRow(new Object[]{teamId, coach, assistantCoach, teamNameResult, playerIdList}); 
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
    public void closeConnection() {
        DatabaseConnection.closeConnection(connection);
    }

    public JPanel getContentPane() {
        return this;
    }
}