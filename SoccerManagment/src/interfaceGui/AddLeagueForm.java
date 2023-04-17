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

public class AddLeagueForm extends JPanel {

    private JTextField leagueNameField;
    private JTextField lowCutOffField;
    private JTextField highCutOffField;

    public AddLeagueForm() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create form components
        JLabel leagueNameLabel = new JLabel("League Name:");
        add(leagueNameLabel);
        leagueNameField = new JTextField();
        add(leagueNameField);

        JLabel lowCutOffLabel = new JLabel("Low Cut Off:");
        add(lowCutOffLabel);
        lowCutOffField = new JTextField();
        add(lowCutOffField);

        JLabel highCutOffLabel = new JLabel("High Cut Off:");
        add(highCutOffLabel);
        highCutOffField = new JTextField();
        add(highCutOffField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        add(new JPanel());
        add(submitButton);
    }

    private void submitForm() {
        String leagueName = leagueNameField.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date lowCutOff = null;
        java.util.Date highCutOff = null;
        try {
            lowCutOff = sdf.parse(lowCutOffField.getText());
            highCutOff = sdf.parse(highCutOffField.getText());
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date format.");
            return;
        }

        // Create an instance of the LeagueValidation class
        LeagueValidationController validator = new LeagueValidationController();

        // Call the validate method to check the input fields
        List<String> errors = validator.validate(leagueName, lowCutOff, highCutOff);
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(null, errors);
            return;
        }

        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SoccerLeague (LeagueName, LowBDCutOff, HighBDCutOff, NumCoaches) VALUES (?, ?, ?, ?)");
            statement.setString(1, leagueName);
            statement.setDate(2, new java.sql.Date(lowCutOff.getTime()));
            statement.setDate(3, new java.sql.Date(highCutOff.getTime()));
            statement.setInt(4, 0);
            statement.executeUpdate();
            statement.close();
            connection.close();

            // Reset the form after successful submission
            leagueNameField.setText("");
            lowCutOffField.setText("");
            highCutOffField.setText("");

            JOptionPane.showMessageDialog(null, "League added successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding league: " + ex.getMessage());
        }
    }
}