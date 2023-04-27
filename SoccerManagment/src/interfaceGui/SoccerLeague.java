package interfaceGui;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import soccerManagment.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import javax.swing.Timer;

public class SoccerLeague extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private String oldTeamName;

    public SoccerLeague() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 503);
        setTitle("Soccer League Information");

        // Create contentPane with BorderLayout
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Create left panel with tabs
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        contentPane.add(leftPanel, BorderLayout.WEST);

        JButton btnSoccerLeagues = new JButton("Soccer Leagues");
        leftPanel.add(btnSoccerLeagues);

        JButton btnAddL = new JButton("Add League");
        leftPanel.add(btnAddL);

        JButton btnNewView = new JButton("Search");
        leftPanel.add(btnNewView);

        // Create right panel with card layout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new CardLayout(0, 0));
        contentPane.add(rightPanel, BorderLayout.CENTER);

        // Create table panel and add it to the right panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        table = new JTable();
        scrollPane = new JScrollPane(table);
        model = new CustomTableModel(new String[]{"LeagueName", "HighBDCutOff", "LowBDCutOff", "NumCoaches", "MinPlayers", "MaxPlayers", "TeamList"}, 0);
        table.setModel(model);

        // Added TableModelListener to save edits to the database
        // Value corresponds to the league name which is stored in the oldTeamName variable
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        oldTeamName = (String) model.getValueAt(selectedRow, 0);
                    }
                }
            }
        });

        //listener listens for changes in the table model data for updated cells
        //e is used as an object passed as an argument to the tableChanged
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    String columnName = model.getColumnName(col);
                    Object newData = model.getValueAt(row, col);
                    String leagueName = (String) model.getValueAt(row, 0);
                    updateDatabase(oldTeamName, columnName, newData);
                }
            }
        });

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(tablePanel, "soccerLeagues");
        
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton btnUpdate = new JButton("Update");
        btnPanel.add(btnUpdate);
        tablePanel.add(btnPanel, BorderLayout.SOUTH);
        
        JButton btnDelete = new JButton("Delete");
        btnPanel.add(btnDelete);

        // Create other panel and add it to the right panel
        JPanel addLeaguePanel = new JPanel();
        addLeaguePanel.setLayout(new BorderLayout());
        JLabel addLeagueLabel = new JLabel("This is the other panel");
        addLeaguePanel.add(addLeagueLabel, BorderLayout.CENTER);
        rightPanel.add(addLeaguePanel, "other");
        
        //Call for updates
        updateTable();

        // Set up tab button actions
        btnSoccerLeagues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "soccerLeagues");
            }
        });
        
        //Add  League action
        btnAddL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddLeagueForm addLeagueForm = new AddLeagueForm();
                rightPanel.add(addLeagueForm, "addLeague");
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "addLeague");
            }
        });
        
        //League search action
        btnNewView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LeagueSearch leagueSearchPanel = new LeagueSearch(rightPanel);
                rightPanel.add(leagueSearchPanel, "leagueSearch");
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "leagueSearch");
            }
        });
        
        //Updates the table manually if clicked 
        //Needed since table is no longer auto updated 
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        
        //Delete button actions for the leagues, row must be selected for deletion 
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the name of league from the selected row
                    String leagueName = (String) model.getValueAt(selectedRow, 0);
                    
                    // Remove the selected row from the table model
                    model.removeRow(selectedRow);
                    
                    // Delete the selected row from the database
                    deleteRowFromDatabase(leagueName);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No row selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });      
    }
    
    public void updateTable() {
        // Clear the current contents of the table model
        model.setRowCount(0); 

        try {
        	//Display of all leagues present
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerLeague");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String leagueName = resultSet.getString("LeagueName");
                Date lowDBCutOff = resultSet.getDate("LowBDCutOff");
                Date highDBCutOff = resultSet.getDate("HighBDCutOff");
                int minPlayers = resultSet.getInt("MinPlayers");
                int maxPlayers = resultSet.getInt("MaxPlayers");
                
                // Retrieve the TeamList column from the ResultSet
                String teamList = resultSet.getString("TeamList");
                
                // Count the number of teams in the TeamList
                int numTeams = teamList.isEmpty() ? 0 : teamList.split(",").length;

                // Calculate the number of coaches based on the number of teams
                //Incremented by 2 for assistant and reg. coach
                int numCoaches = numTeams * 2;
                
                // Format the dates as Simple dates 
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Adds a new row to the table model with the retrieved data
                model.addRow(new Object[]{leagueName, dateFormat.format(highDBCutOff), dateFormat.format(lowDBCutOff), numCoaches, minPlayers, maxPlayers, teamList});
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
    }
    
    private void updateDatabase(String oldLeagueName, String columnName, Object newData) {
        // Check if the updated column is a date column and validate the date format
    	// Requires the user to have the dates be in the correct format
    	// Previously was allowing any update to be made to them
        if (columnName.equals("HighBDCutOff") || columnName.equals("LowBDCutOff")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse((String) newData);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date in the format yyyy-MM-dd.", "Invalid Date Format", JOptionPane.WARNING_MESSAGE);
                updateTable();
                return;
            }
        }

        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE SoccerLeague SET " + columnName + " = ? WHERE LeagueName = ?");
            statement.setObject(1, newData);
            statement.setString(2, oldLeagueName);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Using previous delete controller that was already implemented in league search
    private void deleteRowFromDatabase(String leagueName) {
        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM SoccerLeague WHERE LeagueName = ?");
            statement.setString(1, leagueName);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public class CustomTableModel extends DefaultTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//takes two arguments being column names and row number and then passes them to the superclass constructor
		public CustomTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        //Override for isCellEditable
        //Used for making the list of Teams non editable
        public boolean isCellEditable(int row, int column) {
            if (column == getColumnCount() - 1) {
                return false;
            }
            return super.isCellEditable(row, column);
        }
    }   
}