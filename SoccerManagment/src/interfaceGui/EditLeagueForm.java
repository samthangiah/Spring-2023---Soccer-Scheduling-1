package interfaceGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import interfaceGui.LeagueSearch;
import soccerManagment.DatabaseConnection;

public class EditLeagueForm extends JPanel {

    private JTextField leagueNameTextField;
    private JTextField lowDBCutOffTextField;
    private JTextField highDBCutOffTextField;
    private JTextField minPlayersTextField;
    private JTextField maxPlayersTextField;
    private JButton backButton;
    private JButton submitButton;
    private JPanel rightPanel;
    private String originalLeagueName; 
    private LeagueSearch leagueSearch;

    public EditLeagueForm(JPanel rightPanel, LeagueSearch leagueSearch, String leagueName, String lowDBCutOff, String highDBCutOff, int numCoaches, int minPlayers, int maxPlayers) {
        this.rightPanel = rightPanel;
        this.leagueSearch = leagueSearch;
        // Storing the original league name here
        //Needed for finding selected league from database
        originalLeagueName = leagueName; 

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2));
        add(formPanel, BorderLayout.CENTER);

        formPanel.add(new JLabel("League Name:"));
        leagueNameTextField = new JTextField(leagueName);
        formPanel.add(leagueNameTextField);

        formPanel.add(new JLabel("Low DB Cut Off:"));
        lowDBCutOffTextField = new JTextField(lowDBCutOff);
        formPanel.add(lowDBCutOffTextField);

        formPanel.add(new JLabel("High DB Cut Off:"));
        highDBCutOffTextField = new JTextField(highDBCutOff);
        formPanel.add(highDBCutOffTextField);

        formPanel.add(new JLabel("Minimum Players:"));
        minPlayersTextField = new JTextField(Integer.toString(minPlayers));
        formPanel.add(minPlayersTextField);

        formPanel.add(new JLabel("Maximum Players:"));
        maxPlayersTextField = new JTextField(Integer.toString(maxPlayers));
        formPanel.add(maxPlayersTextField);

        backButton = new JButton("Back");
        formPanel.add(backButton);

        submitButton = new JButton("Submit");
        formPanel.add(submitButton);

        //Go back to search for league
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "leagueSearch");
            }
        });
        //Submitting changes made to league if present
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String leagueName = leagueNameTextField.getText();
                String lowDBCutOffString = lowDBCutOffTextField.getText();
                String highDBCutOffString = highDBCutOffTextField.getText();
                int minPlayers = Integer.parseInt(minPlayersTextField.getText());
                int maxPlayers = Integer.parseInt(maxPlayersTextField.getText());

                //Fixing min and max players to not be negative
                if (minPlayers < 0 || maxPlayers < 0) {
                    JOptionPane.showMessageDialog(null, "Min and max players must be non-negative integers.");
                    return;
                }
                
                try {
                    Connection connection = DatabaseConnection.openConnection();

                    // Retrieve the current league information from the database
                    PreparedStatement selectStatement = connection.prepareStatement("SELECT LeagueName, LowBDCutOff, HighBDCutOff, NumCoaches, MinPlayers, MaxPlayers FROM SoccerLeague WHERE LeagueName=?");
                    // Using the original league name here
                    selectStatement.setString(1, originalLeagueName); 
                    ResultSet resultSet = selectStatement.executeQuery();

                    // Check if the league exists in the database
                    if (resultSet.next()) {
                        // Retrieve the current league information from the database
                        String currentLeagueName = resultSet.getString("LeagueName");
                        Date currentLowDBCutOff = resultSet.getDate("LowBDCutOff");
                        Date currentHighDBCutOff = resultSet.getDate("HighBDCutOff");
                        int currentNumCoaches = resultSet.getInt("NumCoaches");
                        int currentMinPlayers = resultSet.getInt("MinPlayers");
                        int currentMaxPlayers = resultSet.getInt("MaxPlayers");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date lowDBCutOffUtilDate = sdf.parse(lowDBCutOffString);
                        java.sql.Date newLowDBCutOff = new java.sql.Date(lowDBCutOffUtilDate.getTime());
                        java.util.Date highDBCutOffUtilDate = sdf.parse(highDBCutOffString);
                        java.sql.Date newHighDBCutOff = new java.sql.Date(highDBCutOffUtilDate.getTime());

                        // Check if any values have changed
                        if (!currentLeagueName.equals(leagueName) || currentLowDBCutOff.getTime() != newLowDBCutOff.getTime() || currentHighDBCutOff.getTime() != newHighDBCutOff.getTime() || currentNumCoaches != numCoaches || currentMinPlayers != minPlayers || currentMaxPlayers != maxPlayers) {
                            // Update the SoccerLeague table with the new values if changes were made
                            PreparedStatement updateStatement = connection.prepareStatement("UPDATE SoccerLeague SET LeagueName=?, LowBDCutOff=?, HighBDCutoff=?, NumCoaches=?, MinPlayers=?, MaxPlayers=? WHERE LeagueName=?");
                            updateStatement.setString(1, leagueName);
                            updateStatement.setDate(2, newLowDBCutOff);
                            updateStatement.setDate(3, newHighDBCutOff);
                            updateStatement.setInt(4, numCoaches);
                            updateStatement.setInt(5, minPlayers);
                            updateStatement.setInt(6, maxPlayers);
                            updateStatement.setString(7, originalLeagueName);
                            int rowsUpdated = updateStatement.executeUpdate();
                            
                            if (minPlayers > 0) {
                            	// Refresh the search results
                                //JOptionPane.showMessageDialog(null, "League updated successfully.");
                                leagueSearch.refreshSearchResults(); 
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update league because of Min players being negative.");
                            }
                            
                            if (maxPlayers > 0) {
                            	// Refresh the search results
                                //JOptionPane.showMessageDialog(null, "League updated successfully.");
                                leagueSearch.refreshSearchResults(); 
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update league because of Max players being negative.");
                            }

                            if (rowsUpdated > 0) {
                            	// Refresh the search results
                                JOptionPane.showMessageDialog(null, "League updated successfully.");
                                leagueSearch.refreshSearchResults(); 
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update league.");
                            }

                            updateStatement.close();
                            // Check to see if edits were actually made, if not, doesn't update
                        	} else {
                            JOptionPane.showMessageDialog(null, "No changes were made.");
                        	}
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "League not found.");
                    }

                    resultSet.close();
                    selectStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    //Making sure date format is in yyyy-MM-dd format or not acceptable
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format.");
                }
            }
        });
    }
}