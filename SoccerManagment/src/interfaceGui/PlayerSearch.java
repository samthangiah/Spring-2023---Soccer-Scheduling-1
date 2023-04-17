package interfaceGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import soccerManagment.DatabaseConnection;
import soccerManagment.DeletePlayerController;

public class PlayerSearch extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField lastNameField;
    private JTable resultsTable;
    private JButton editButton;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private JButton deleteButton;

    public PlayerSearch() {
        setBounds(0, 0, 808, 430);
        setLayout(null);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(10, 10, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(100, 10, 200, 25);
        add(lastNameField);

        searchButton = new JButton("Search");
        searchButton.setBounds(310, 10, 100, 25);
        add(searchButton);

        editButton = new JButton("Edit");
        editButton.setBounds(420, 10, 100, 25);
        add(editButton);
        editButton.setEnabled(false);

        // Initialize table with column names
        String[] columnNames = {"PlayerID", "FirstName", "LastName", "Birthdate", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Player ID column is not editable
                return column != 0;
            }
        };
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(10, 50, 780, 440);
        add(scrollPane);
        
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.setBounds(541, 10, 100, 25);
        add(deleteButton);

        // Add ListSelectionListener to enable/disable the edit button
        resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Check if a row is selected
                    if (resultsTable.getSelectedRow() != -1) {
                        // A row is selected, enable the edit and delete buttons
                        editButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    } else {
                        // No row is selected, disable the edit and delete buttons
                        editButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
                }
            }
        });
        
        

        // Add action listener to search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the table
                tableModel.setRowCount(0);

                String lastName = lastNameField.getText();

                // Fetch the data from the database using the last name
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet resultSet = null;

                try {
                    connection = DatabaseConnection.openConnection();
                    statement = connection.prepareStatement("SELECT * FROM PlayerInformation WHERE `LastName` LIKE ?");
                    statement.setString(1, "%" + lastName + "%");
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int playerId = resultSet.getInt("PlayerId");
                        String firstName = resultSet.getString("FirstName");
                        String lastName1 = resultSet.getString("LastName");
                        Date birthdate = resultSet.getDate("Birthdate");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedBirthdate = dateFormat.format(birthdate);                        String gender = resultSet.getString("Gender");
                        int skillLevel = resultSet.getInt("SkillLevel");
                        int seasonsPlayed = resultSet.getInt("SeasonsPlayed");
                        String registered = resultSet.getString("Registered");
                        String assigned = resultSet.getString("Assigned");

                        // Add the retrieved data to the table
                        tableModel.addRow(new Object[]{playerId, firstName, lastName1, formattedBirthdate, gender, skillLevel, seasonsPlayed, registered, assigned});
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected player ID from the table
                int row = resultsTable.getSelectedRow();
                int playerId = (int) tableModel.getValueAt(row, 0);

                // Open the EditPlayerForm for the selected player
                JFrame editPlayerFrame = new EditPlayerForm(playerId);
                editPlayerFrame.setVisible(true);
                
                // Close the PlayerSearch window
                //SwingUtilities.getWindowAncestor(editButton).dispose();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected player ID from the table
                int row = resultsTable.getSelectedRow();
                int playerId = (int) tableModel.getValueAt(row, 0);

                // Call the DeletePlayerController to delete the player from the database
                DeletePlayerController controller = new DeletePlayerController();
                controller.deletePlayer(playerId);

                // Remove the row from the table model
                tableModel.removeRow(row);
     
                
            }
        });

    }
    
}