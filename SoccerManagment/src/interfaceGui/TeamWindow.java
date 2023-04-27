package interfaceGui;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import soccerManagment.DeleteTeamController;
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

//Window for all teams present 
//Now has the features of being able to edit a team 
//Teams cannot have numbers used at all for editing cells
//Max and min determined by league team is present in 
public class TeamWindow extends JFrame {
    private Connection connection;
    private JTable soccerTeamsTable;
    private JScrollPane soccerTeamsTableScrollPane;
    private DefaultTableModel soccerTeamsTableModel;
    private AddTeamForm addTeamForm;
    private Timer timer;

    public TeamWindow() {
        setTitle("Team Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create left panel with tab buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        getContentPane().add(leftPanel, BorderLayout.WEST);

        //Left hand tabs 
        JButton btnTab1 = new JButton("Add Team");
        leftPanel.add(btnTab1);

        JButton btnTab2 = new JButton("Search");
        leftPanel.add(btnTab2);
        
        JButton btnUpdate = new JButton("Update");

        JButton btnOriginalTab = new JButton("Team List");
        leftPanel.add(btnOriginalTab);

        // Create right panel with contents
        //Displaying the team list
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        getContentPane().add(rightPanel, BorderLayout.CENTER);

        // Create new card content for Tab 1
        JPanel tab1Content = new JPanel();
        
        //Update button used instead of auto updating 
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnUpdate);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Create button for deleting selected row
        JButton btnDelete = new JButton("Delete");
        bottomPanel.add(btnDelete);

        // Create new content for Original Tab displaying the teams 
        JPanel originalTabContent = new JPanel();
        originalTabContent.add(new JLabel("Original Content"));
        addTeamForm = new AddTeamForm();
        soccerTeamsTableModel = new CustomTableModel(new String[]{"TeamID", "Coach", "AssistantCoach", "TeamName", "PlayerIdList"}, 0);
        soccerTeamsTable = new JTable(soccerTeamsTableModel);
        soccerTeamsTableScrollPane = new JScrollPane(soccerTeamsTable);
        rightPanel.add(soccerTeamsTableScrollPane, BorderLayout.CENTER);
        
        //Disallow usage of numbers or special characters for the editing of the 3 columns 
        //Coach, Assistant coach, Team Name. All should only be alpha characters 
        DefaultCellEditor letterOnlyCellEditor = new DefaultCellEditor(new JTextField()) {
            {
                ((JTextField) getComponent()).setDocument(new PlainDocument() {
                    @Override
                    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                        if (str != null && str.matches("^[a-zA-Z\\s]*$")) {
                            super.insertString(offs, str, a);
                        }
                    }
                });
            }
        };
        //Coach
        soccerTeamsTable.getColumnModel().getColumn(1).setCellEditor(letterOnlyCellEditor);
        //AssistantCoach
        soccerTeamsTable.getColumnModel().getColumn(2).setCellEditor(letterOnlyCellEditor);
        //TeamName
        soccerTeamsTable.getColumnModel().getColumn(3).setCellEditor(letterOnlyCellEditor);
        
        //Listener for changes to cells
        soccerTeamsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object newValue = soccerTeamsTableModel.getValueAt(row, column);

                    // Get the TeamID for the row that was edited by user
                    int teamId = (Integer) soccerTeamsTableModel.getValueAt(row, 0);

                    // Update the table in the database
                    // Cases used to determine which of the strings was changed
                    try {
                        // Determine the column name based on the column index
                        String columnName;
                        switch (column) {
                            case 1:
                                columnName = "Coach";
                                break;
                            case 2:
                                columnName = "AssistantCoach";
                                break;
                            case 3:
                                columnName = "TeamName";
                                break;
                             // Do not update the database if the column for teamID and playerIdList
                            default:
                                return;                             
                        }

                        // SQL for updating changed cell determined from cell index
                        PreparedStatement statement = connection.prepareStatement(
                            "UPDATE SoccerTeams SET " + columnName + " = ? WHERE TeamID = ?"
                        );
                        statement.setObject(1, newValue);
                        statement.setInt(2, teamId);

                        // Execute the update and display a message with the result
                        //Determining what row was changed and printing, used for testing
                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            //System.out.println("Successfully updated row " + row + ". Column: " + columnName);
                        } else {
                            //System.out.println("Failed to update row " + row + ". Column: " + columnName);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        //System.out.println("Error updating row " + row + ". Column: " + columnName);
                    }
                }
            }
        });

        //Add team button
        btnTab1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Component currentComponent = rightPanel.getComponent(0);
                if (currentComponent == addTeamForm.getContentPane()) {
                    rightPanel.setComponentZOrder(currentComponent, 0);
                } else {
                    rightPanel.remove(currentComponent);
                    rightPanel.add(addTeamForm.getContentPane(), BorderLayout.CENTER);
                }
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        //Search for team button
        btnTab2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                TeamSearch teamSearch = new TeamSearch();
                rightPanel.add(teamSearch, BorderLayout.CENTER);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        //original team window view button
        //Add save button back for when tabs were switched 
        btnOriginalTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                rightPanel.add(soccerTeamsTableScrollPane, BorderLayout.CENTER);
                rightPanel.add(bottomPanel, BorderLayout.SOUTH);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });
        
        //Update button implemented now that auto update is no longer used
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    displaySoccerTeams(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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

        //end connection with closing of Window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                DatabaseConnection.closeConnection(connection);
            }
        });
        
        //Delete button actions 
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = soccerTeamsTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the TeamID from the selected row
                    int teamId = (Integer) soccerTeamsTableModel.getValueAt(selectedRow, 0);
                    try {
                        // Calling the deleteTeam method from the DeleteTeamController
                        DeleteTeamController.deleteTeam(teamId);
                        
                        // Remove the deleted row from the table model
                        soccerTeamsTableModel.removeRow(selectedRow);
                        
                        // Update the table to reflect the changes
                        soccerTeamsTable.revalidate();
                        soccerTeamsTable.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    //Displayed if delete is selected without row selected
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No row selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    
    //returns a set of all the team IDs currently displayed in the soccerTeamsTableModel
    //hashSet used to store info of team IDs
    public Set<Integer> getTeamIds() {
        Set<Integer> teamIds = new HashSet<>();
        int rowCount = soccerTeamsTableModel.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
            teamIds.add((Integer) soccerTeamsTableModel.getValueAt(i, 0));
        }
        return teamIds;
    }
    
    //Display of soccer teams in SoccerTeams table 
    private void displaySoccerTeams(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        soccerTeamsTableModel.setRowCount(0);

        //Displaying of all teams present
        try {
            String sql = "SELECT * FROM SoccerTeams";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	// Fetch the TeamID column value
                int teamId = resultSet.getInt("TeamID"); 
                String coach = resultSet.getString("Coach");
                String assistantCoach = resultSet.getString("AssistantCoach");
                String teamName = resultSet.getString("TeamName");

                // Fetch the PlayerIDs with the same Team ID
                PreparedStatement playerStatement = connection.prepareStatement("SELECT PlayerId FROM PlayerInformation WHERE TeamId = ?");
                playerStatement.setInt(1, teamId);
                ResultSet playerResultSet = playerStatement.executeQuery();

                //used to build a list of player IDs associated with each team
                StringBuilder playerIdListBuilder = new StringBuilder();
                while (playerResultSet.next()) {
                    int playerId = playerResultSet.getInt("PlayerId");
                    playerIdListBuilder.append(playerId).append(", ");
                }
                // Remove the trailing comma and space
                String playerIdList = playerIdListBuilder.toString().replaceAll(", $", ""); 
                
                // Now includes teamId, coach, assistant coach, team name, and list of players on the team in the row data
                soccerTeamsTableModel.addRow(new Object[]{teamId, coach, assistantCoach, teamName, playerIdList}); 

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
    //created to override the default behavior of cell editing in the table
    class CustomTableModel extends DefaultTableModel {
        public CustomTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Make the "TeamID" and "PlayerIdList" columns non-editable
            return column != 0 && column != 4;
        }
    }
}