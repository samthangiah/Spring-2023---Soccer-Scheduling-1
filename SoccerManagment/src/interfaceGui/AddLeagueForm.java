package interfaceGui;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import soccerManagment.DatabaseConnection;
import soccerManagment.LeagueValidationController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//For for adding league to database
public class AddLeagueForm extends JPanel {
	//Private fields
    private JTextField leagueNameField;
    private JTextField lowCutOffField;
    private JTextField highCutOffField;
    private JTextField minPlayersField;
    private JTextField maxPlayersField;

    public AddLeagueForm() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form leagueName
        JLabel leagueNameLabel = new JLabel("League Name:");
        add(leagueNameLabel);
        leagueNameField = new JTextField();
        add(leagueNameField);
        
        //Low cut off date 
        JLabel lowCutOffLabel = new JLabel("Low Cut Off:");
        add(lowCutOffLabel);
        lowCutOffField = new JTextField();
        add(lowCutOffField);

        //high cut off date
        JLabel highCutOffLabel = new JLabel("High Cut Off:");
        add(highCutOffLabel);
        highCutOffField = new JTextField();
        add(highCutOffField);

        //Min players
        JLabel minPlayersLabel = new JLabel("Min Players:");
        add(minPlayersLabel);
        minPlayersField = new JTextField();
        add(minPlayersField);

        //Max players
        JLabel maxPlayersLabel = new JLabel("Max Players:");
        add(maxPlayersLabel);
        maxPlayersField = new JTextField();
        add(maxPlayersField);

        //Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        add(new JPanel());
        add(submitButton);
    }

    //Submission of input from the league form
    private void submitForm() {
    	//Submission form
        String leagueName = leagueNameField.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date lowCutOff = null;
        java.util.Date highCutOff = null;
        //Date must be in yyyy-MM-dd form
        try {
            lowCutOff = sdf.parse(lowCutOffField.getText());
            highCutOff = sdf.parse(highCutOffField.getText());
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date format.");
            return;
        }

        //Min and max players set to 0 and must not be negative
        int minPlayers = 0;
        int maxPlayers = 0;
        try {
            minPlayers = Integer.parseInt(minPlayersField.getText());
            maxPlayers = Integer.parseInt(maxPlayersField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
            return;
        }

        // Create an instance of the LeagueValidation class
        LeagueValidationController validator = new LeagueValidationController();

        // Call the validate method to check the input fields
        //Check for errors and display
        List<String> errors = validator.validate(leagueName, lowCutOff, highCutOff, minPlayers, maxPlayers);
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(null, errors);
            return;
        }

        try {
            Connection connection = DatabaseConnection.openConnection();
            
            // Updated SQl statement including all league columns
            //SoccerLeague
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SoccerLeague (LeagueName, HighBDCutOff, LowBDCutOff, NumCoaches, MinPlayers, MaxPlayers, TeamList) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, leagueName);
            statement.setDate(2, new java.sql.Date(highCutOff.getTime()));
            statement.setDate(3, new java.sql.Date(lowCutOff.getTime()));
            statement.setInt(4, 0); // Set NumCoaches value to 0
            statement.setInt(5, minPlayers); // Set MinPlayers value
            statement.setInt(6, maxPlayers); // Set MaxPlayers value
            statement.setString(7, ""); // Set TeamList value to an empty string

            statement.executeUpdate();
            statement.close();
            connection.close();

            // Reset the form after successful submission
            leagueNameField.setText("");
            lowCutOffField.setText("");
            highCutOffField.setText("");
            minPlayersField.setText("");
            maxPlayersField.setText("");
            
            //Display success or error when adding
            JOptionPane.showMessageDialog(null, "League added successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding league: " + ex.getMessage());
        }
    }
}