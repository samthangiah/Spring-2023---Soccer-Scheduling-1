package interfaceGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import soccerManagment.DatabaseConnection;
import soccerManagment.DeleteLeagueController;

public class LeagueSearch extends JPanel {

    private JTextField searchTextField;
    private JButton searchButton;
    private JTable searchResultsTable;
    private DefaultTableModel searchResultsTableModel;
    private JButton editButton_1;
    private EditLeagueForm editLeagueForm;
    private JPanel rightPanel;

    public LeagueSearch(JPanel rightPanel) {
        this.rightPanel = rightPanel;
        setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(searchPanel, BorderLayout.NORTH);

        searchTextField = new JTextField(20);
        searchPanel.add(searchTextField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        editButton_1 = new JButton("Edit");
        searchPanel.add(editButton_1);
        
        JButton deleteButton = new JButton("Delete");
        searchPanel.add(deleteButton);

        // Create table for search results
        searchResultsTableModel = new DefaultTableModel(new String[]{"LeagueName", "LowDBCutOff", "HighDBCutOff", "NumCoaches", "MinPlayers", "MaxPlayers"}, 0);
        searchResultsTable = new JTable(searchResultsTableModel);
        JScrollPane searchResultsScrollPane = new JScrollPane(searchResultsTable);
        add(searchResultsScrollPane, BorderLayout.CENTER);

        // Set up search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchLeagues();
            }
        });

        //Set up action for edit button
        editButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = searchResultsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String leagueName = (String) searchResultsTable.getValueAt(selectedRow, 0);
                    Date lowDBCutOff = (Date) searchResultsTable.getValueAt(selectedRow, 1);
                    Date highDBCutOff = (Date) searchResultsTable.getValueAt(selectedRow, 2);
                    int numCoaches = (int) searchResultsTable.getValueAt(selectedRow, 3);
                    int minPlayers = (int) searchResultsTable.getValueAt(selectedRow, 4);
                    int maxPlayers = (int) searchResultsTable.getValueAt(selectedRow, 5);
                    
                    // Format the dates as strings
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String lowDBCutOffStr = dateFormat.format(lowDBCutOff);
                    String highDBCutOffStr = dateFormat.format(highDBCutOff);

                    editLeagueForm = new EditLeagueForm(rightPanel, LeagueSearch.this, leagueName, lowDBCutOffStr, highDBCutOffStr, numCoaches, minPlayers, maxPlayers);
                    rightPanel.add(editLeagueForm, "editLeague");

                    CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                    cardLayout.show(rightPanel, "editLeague");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });
        //Action for delete button
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = searchResultsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String leagueName = (String) searchResultsTable.getValueAt(selectedRow, 0);
                    DeleteLeagueController.deleteLeague(leagueName);
                    // Refresh search results after deletion
                    searchLeagues(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });
    }

    public void refreshSearchResults() {
        searchLeagues();
    }
    //Search for desired league
    public void searchLeagues() {
        String searchTerm = searchTextField.getText().trim();
        searchResultsTableModel.setRowCount(0);

        try {
            Connection connection = DatabaseConnection.openConnection();
            //Select via league name
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerLeague WHERE LeagueName LIKE ?");
            statement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String leagueName = resultSet.getString("LeagueName");
                Date lowDBCutOff = resultSet.getDate("LowBDCutOff");
                Date highDBCutOff = resultSet.getDate("HighBDCutOff");
                int numCoaches = resultSet.getInt("NumCoaches");
                int minPlayers = resultSet.getInt("MinPlayers");
                int maxPlayers = resultSet.getInt("MaxPlayers");

                searchResultsTableModel.addRow(new Object[]{leagueName, lowDBCutOff, highDBCutOff, numCoaches, minPlayers, maxPlayers});
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}