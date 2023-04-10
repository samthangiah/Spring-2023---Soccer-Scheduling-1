package interfaceGui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;

public class PlayerSearch extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField lastNameField;
    private JTable resultsTable;
    private JButton editButton;
    private JButton searchButton;
    private DefaultTableModel tableModel;

    public PlayerSearch() {
        setTitle("Search Player");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(10, 10, 100, 25);
        contentPane.add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(100, 10, 200, 25);
        contentPane.add(lastNameField);

        //Button for playerSearch
        //Hidden for automatic search/update
        searchButton = new JButton("Search");
        searchButton.setBounds(310, 10, 100, 25);
        contentPane.add(searchButton);
        searchButton.setVisible(false);

        editButton = new JButton("Edit");
        editButton.setBounds(420, 10, 100, 25);
        contentPane.add(editButton);
        editButton.setEnabled(false);

        // Initialize table with column names
        String[] columnNames = {"Player ID", "First Name", "Last Name", "Birthdate", "Gender", "Skill Level", "Registered", "Assigned"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(10, 50, 820, 300);
        contentPane.add(scrollPane);

        // Add ListSelectionListener to enable/disable the edit button
        resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && resultsTable.getSelectedRow() != -1) {
                    // A row is selected
                    editButton.setEnabled(true);
                } else {
                    // No row is selected
                    editButton.setEnabled(false);
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultsTable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    // Get the player ID from the selected row
                    int playerId = (int) resultsTable.getModel().getValueAt(selectedRow, 0);

                    // Create and display the edit player form
                    EditPlayerForm editPlayerForm = new EditPlayerForm(playerId);
                    editPlayerForm.setVisible(true);
                }
            }
        });
        // Set up the search button action listener
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
                    statement = connection.prepareStatement("SELECT * FROM PlayerInformation WHERE LastName = ?");
                    statement.setString(1, lastName);
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int playerId = resultSet.getInt("PlayerId");
                        String firstName = resultSet.getString("FirstName");
                        String lastName1 = resultSet.getString("LastName");
                        String birthdate = resultSet.getString("Birthdate");
                        String gender = resultSet.getString("Gender");
                        int skillLevel = resultSet.getInt("SkillLevel");
                        String registered = resultSet.getString("Registered");
                        String assigned = resultSet.getString("Assigned");

                        // Add the retrieved data to the table
                        tableModel.addRow(new Object[]{playerId, firstName, lastName1, birthdate, gender, skillLevel, registered, assigned});
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

        Timer timer = new Timer(4000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        timer.start();
    }

    private void updateTable() {
        String lastName = lastNameField.getText();
        if (!lastName.isEmpty()) {
            searchButton.doClick(); // Simulate clicking the search button to update the table
        }
    }
}