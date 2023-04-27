package interfaceGui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import soccerManagment.DatabaseConnection;
import soccerManagment.DeletePlayerController;
import interfaceGui.AddPlayerForm;
import interfaceGui.PlayerSearch;
import interfaceGui.ResetDatabase;
import interfaceGui.LeagueFormReport;

//Used to display list of available players 
//Design issue when adding player list button to top tool bar 
public class PlayersList extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    public PlayersList() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 503);
        setTitle("Player Information");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Add a JToolBar at the top of the contentPane
        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);
        contentPane.add(topToolBar, BorderLayout.NORTH);

        JButton btnPlayerList = new JButton("Player List");
        topToolBar.add(btnPlayerList);
        
        // Add the "Team Window" button to the topToolBar
        JButton btnTeamWindow = new JButton("Team Window");
        topToolBar.add(btnTeamWindow);

        // Add the "Soccer League" button to the topToolBar
        JButton btnSoccerLeague = new JButton("Soccer League");
        topToolBar.add(btnSoccerLeague);

        // Add the "Reset Database" button to the topToolBar
        JButton btnResetDatabase = new JButton("Reset Database");
        topToolBar.add(btnResetDatabase);
        
        // Add the "Reset Database" button to the topToolBar
        JButton btnLeagueReport = new JButton("LeagueReport");
        topToolBar.add(btnLeagueReport);

        // Create left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);

        // Create playerListButtonPanel with tabs
        JPanel playerListButtonPanel = new JPanel();
        playerListButtonPanel.setLayout(new GridLayout(3, 1));
        playerListButtonPanel.setVisible(true);
        leftPanel.add(playerListButtonPanel, BorderLayout.CENTER);

        //Left tabs present on the player list 
        JButton btnAddPlayer = new JButton("Add Player");
        playerListButtonPanel.add(btnAddPlayer);

        JButton btnSearch = new JButton("Search");
        playerListButtonPanel.add(btnSearch);

        JButton btnAllPlayers = new JButton("All Players");
        playerListButtonPanel.add(btnAllPlayers);
        
        JPanel updateButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnUpdate = new JButton("Update");
        updateButtonPanel.add(btnUpdate);
        JButton btnDelete = new JButton("Delete");
        updateButtonPanel.add(btnDelete);
        
        // Create right panel with card layout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new CardLayout(0, 0));
        contentPane.add(rightPanel, BorderLayout.CENTER);

        // Create table panel and add it to the right panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        table = new JTable();
        scrollPane = new JScrollPane(table);
        model = new DefaultTableModel(new String[]{"Player ID", "First Name", "Last Name", "Birthdate", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned", "Team ID", "League"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells editable except for the PlayerID, assigned, registered, team ID
            	if (column == 0 || column == 7 || column == 8 || column == 9 || column == 10) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        //right panel list of players 
        table.setModel(model);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(tablePanel, "playerList");
      
        //Addition of update button
        tablePanel.add(updateButtonPanel, BorderLayout.SOUTH);


        // Add PlayerForm panel
        AddPlayerForm addPlayerForm = new AddPlayerForm();
        rightPanel.add(addPlayerForm, "addPlayerForm");

        // Add PlayerSearch panel
        PlayerSearch playerSearch = new PlayerSearch();
        rightPanel.add(playerSearch, "playerSearch");

        // Add TeamWindow instance to the rightPanel
        //Issues with design, when commented out design can be edited. Added back in last when design completed
        TeamWindow teamWindow = new TeamWindow();
        rightPanel.add(teamWindow.getContentPane(), "teamWindow");
        
        //Call for update on load 
        updateTable();

        // Set up tab button actions
        btnAddPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "addPlayerForm");
            }
        });
        
        //Update button for updating now that auto update is not present
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        
        btnPlayerList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Make the playerListButtonPanel visible when the "Player List" button is clicked
                playerListButtonPanel.setVisible(true);

                // Show the "playerList" panel when the "Player List" button is clicked
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "playerList");
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "playerSearch");
            }
        });

        btnTeamWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "teamWindow");
                playerListButtonPanel.setVisible(false);
            }
        });

        btnSoccerLeague.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                SoccerLeague soccerLeague = new SoccerLeague();
                rightPanel.add(soccerLeague.getContentPane(), "soccerLeague");
                cardLayout.show(rightPanel, "soccerLeague");
                playerListButtonPanel.setVisible(false);
            }
        });
        
        btnLeagueReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                LeagueFormReport leagueFormReport = new LeagueFormReport();
                rightPanel.add(leagueFormReport, "leagueFormReport");
                cardLayout.show(rightPanel, "leagueFormReport");
                playerListButtonPanel.setVisible(false);
            }
        });

        btnResetDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResetDatabase.resetPlayerStatus();
            }
        });
        
        btnPlayerList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerListButtonPanel.setVisible(!playerListButtonPanel.isVisible());
            }
        });
        
        //action for retrieving all players from tab
        btnAllPlayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "playerList");
                playerListButtonPanel.setVisible(true);
            }
        });
        
        //Delete button actions for selected row in player list
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) { // Check if a row is selected
                    int playerId = (int) table.getValueAt(selectedRow, 0);
                    DeletePlayerController deletePlayerController = new DeletePlayerController();
                    deletePlayerController.deletePlayer(playerId);
                    updateTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No row selected", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //Table update method
    public void updateTable() {
    	// Clear the table
        model.setRowCount(0); 
        try {
        	//Display of all players in the database 
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerInformation");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int playerId = resultSet.getInt("PlayerId");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                Date birthdate = resultSet.getDate("Birthdate");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedBirthdate = dateFormat.format(birthdate);
                String gender = resultSet.getString("Gender");
                int skillLevel = resultSet.getInt("SkillLevel");
                int seasonsPlayed = resultSet.getInt("SeasonsPlayed");
                boolean isRegistered = resultSet.getBoolean("Registered");
                boolean isAssigned = resultSet.getBoolean("Assigned");
                int teamID = resultSet.getInt("TeamID");
                String league = resultSet.getString("League");

                model.addRow(new Object[]{playerId, firstName, lastName, formattedBirthdate, gender, skillLevel, seasonsPlayed, isRegistered, isAssigned, teamID, league});
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            }
        });
        
        //adds a listener to a table model in the list 
        //checks for update and retrieves the row and column of the updated cell
        //Can not get the message to stop printing if multiple errors are made
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object newValue = model.getValueAt(row, column);
                    int playerId = (int) model.getValueAt(row, 0);

                    // Check for correct Birthdate format
                    if (column == 3) {
                        if (!newValue.toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
                            JOptionPane.showMessageDialog(null, "Birthdate format must be yyyy-MM-dd.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Check for correct Gender format
                    if (column == 4) {
                        if (!newValue.toString().equalsIgnoreCase("M") && !newValue.toString().equalsIgnoreCase("F")) {
                            JOptionPane.showMessageDialog(null, "Gender must be 'M' or 'F'.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Check for correct First and Last name format
                    if (column == 1 || column == 2) {
                        if (!newValue.toString().matches("[a-zA-Z]+")) {
                            JOptionPane.showMessageDialog(null, "First and Last names must only contain letters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Check for correct Seasons Played and Skill Level format
                    if (column == 5) {
                        String strValue = newValue.toString();
                        if (!strValue.matches("\\d+") || strValue.length() > 2) {
                            JOptionPane.showMessageDialog(null, "Skill must be a number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int intValue = Integer.parseInt(strValue);
                        if (intValue < 0 || intValue > 10) {
                            JOptionPane.showMessageDialog(null, "Skill must be 0-10.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    if (column == 6) {
                        if (!newValue.toString().matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(null, "Seasons Played must be positive numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    updateDatabase(playerId, column, newValue);
                }
            }
        });    
    } 
    //Update database from old values to new values on the player list if edits are present
    public void updateDatabase(int playerId, int column, Object newValue) {
        String columnName = getColumnNameForUpdate(column);
        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE PlayerInformation SET " + columnName + " = ? WHERE PlayerId = ?");

            if (newValue instanceof Date) {
                statement.setDate(1, new java.sql.Date(((Date) newValue).getTime()));
            } else if (newValue instanceof Integer) {
                statement.setInt(1, (int) newValue);
            } else if (newValue instanceof Boolean) {
                statement.setBoolean(1, (boolean) newValue);
            } else {
                statement.setString(1, newValue.toString());
            }

            statement.setInt(2, playerId);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Handling spacing for columns in the database
    public String getColumnNameForUpdate(int column) {
        String columnName = model.getColumnName(column);
        switch (columnName) {
            case "Last Name":
                return "LastName";
            case "First Name":
                return "FirstName";
            case "Seasons Played":
                return "SeasonsPlayed";
            case "Skill Level":
                return "SkillLevel";
            default:
                return columnName;
        }
    }
}