package interfaceGui;

//Form for adding new player
//Called from PlayerList 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import soccerManagment.AddPlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class AddPlayerForm extends JFrame {
    private JPanel mainPanel;
    private JTextField playerIdField, firstNameField, lastNameField, skillLevelField, seasonsPlayedField;
    private JFormattedTextField birthdateField;
    private JComboBox<String> genderBox;
    private JCheckBox isRegisteredBox, isAssignedBox;
    private JButton submitButton;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipCodeField;
    private JTextField carPoolField;
    private JTextField leagueField;
    private JTextField jerseySizeField;
    private JTextField shortSizeField;
    private JTextField sockSizeField;
    private JTextField paidField;
    private JTextField medicalInsurerField;
    private JTextField medicalConcernsField;

    //parent/guardian information
    private JTextField adultLastNameField;
    private JTextField adultFirstNameField;
    private JTextField adultPhone1Field;
    private JTextField adultPhone2Field;
    private JTextField adultEmailField;

    public AddPlayerForm() {
        setTitle("Add Player");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(729, 630);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel label = new JLabel("Player ID:");
        label.setBounds(0, 39, 54, 21);
        mainPanel.add(label);
        playerIdField = new JTextField();
        playerIdField.setBounds(64, 39, 74, 21);
        mainPanel.add(playerIdField);

        JLabel label_1 = new JLabel("First Name:");
        label_1.setBounds(148, 39, 77, 21);
        mainPanel.add(label_1);
        firstNameField = new JTextField();
        firstNameField.setBounds(235, 39, 145, 21);
        mainPanel.add(firstNameField);

        JLabel label_2 = new JLabel("Last Name:");
        label_2.setBounds(431, 39, 81, 21);
        mainPanel.add(label_2);
        lastNameField = new JTextField();
        lastNameField.setBounds(517, 39, 162, 21);
        mainPanel.add(lastNameField);

        JLabel label_3 = new JLabel("Birthdate (yyyy-MM-dd):");
        label_3.setBounds(148, 70, 159, 21);
        mainPanel.add(label_3);
        birthdateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        birthdateField.setBounds(288, 70, 133, 21);
        mainPanel.add(birthdateField);

        JLabel label_4 = new JLabel("Gender:");
        label_4.setBounds(0, 70, 54, 21);
        mainPanel.add(label_4);
        String[] genders = {"M", "F"};
        genderBox = new JComboBox<>();
        genderBox.setBounds(64, 70, 74, 21);
        DefaultComboBoxModel<String> genderModel = new DefaultComboBoxModel<>(genders);
        genderBox.setModel(genderModel);
        mainPanel.add(genderBox);

        JLabel label_5 = new JLabel("Skill Level:");
        label_5.setBounds(0, 101, 81, 21);
        mainPanel.add(label_5);
        skillLevelField = new JTextField();
        skillLevelField.setBounds(91, 101, 47, 21);
        mainPanel.add(skillLevelField);

        JLabel label_6 = new JLabel("Seasons Played:");
        label_6.setBounds(187, 101, 193, 21);
        mainPanel.add(label_6);
        seasonsPlayedField = new JTextField();
        seasonsPlayedField.setBounds(288, 101, 39, 21);
        mainPanel.add(seasonsPlayedField);

        JLabel label_7 = new JLabel("Registered:");
        label_7.setBounds(470, 101, 59, 21);
        mainPanel.add(label_7);
        isRegisteredBox = new JCheckBox();
        isRegisteredBox.setBounds(535, 101, 39, 21);
        mainPanel.add(isRegisteredBox);

        JLabel label_8 = new JLabel("Assigned:");
        label_8.setBounds(580, 101, 54, 21);
        mainPanel.add(label_8);
        isAssignedBox = new JCheckBox();
        isAssignedBox.setBounds(640, 101, 39, 21);
        mainPanel.add(isAssignedBox);
        
        JLabel label_9 = new JLabel("Address:");
        label_9.setBounds(0, 134, 54, 21);
        mainPanel.add(label_9);
        addressField = new JTextField();
        addressField.setBounds(64, 134, 186, 21);
        mainPanel.add(addressField);

        JLabel label_10 = new JLabel("City:");
        label_10.setBounds(276, 132, 31, 21);
        mainPanel.add(label_10);
        cityField = new JTextField();
        cityField.setBounds(307, 134, 73, 21);
        mainPanel.add(cityField);
        
        JLabel label_11 = new JLabel("State:");
        label_11.setBounds(390, 134, 86, 21);
        mainPanel.add(label_11);
        stateField = new JTextField();
        stateField.setBounds(431, 134, 45, 21);
        mainPanel.add(stateField);
        
        JLabel label_12 = new JLabel("Zip:");
        label_12.setBounds(491, 134, 31, 21);
        mainPanel.add(label_12);
        zipCodeField = new JTextField();
        zipCodeField.setBounds(527, 134, 74, 21);
        mainPanel.add(zipCodeField);
        
        JLabel label_13 = new JLabel("Car Pool:");
        label_13.setBounds(0, 165, 138, 21);
        mainPanel.add(label_13);
        carPoolField = new JTextField();
        carPoolField.setBounds(64, 165, 74, 21);
        mainPanel.add(carPoolField);
        
        
        
        JLabel label_14 = new JLabel("League:");
        label_14.setBounds(151, 165, 99, 21);
        mainPanel.add(label_14);
        leagueField = new JTextField();
        leagueField.setBounds(212, 165, 39, 21);
        mainPanel.add(leagueField);
        
        JLabel label_15 = new JLabel("Jersey Size:");
        label_15.setBounds(0, 192, 138, 21);
        mainPanel.add(label_15);
        jerseySizeField = new JTextField();
        jerseySizeField.setBounds(64, 192, 74, 21);
        mainPanel.add(jerseySizeField);
        
        JLabel label_16 = new JLabel("Short Size:");
        label_16.setBounds(148, 192, 102, 21);
        mainPanel.add(label_16);
        shortSizeField = new JTextField();
        shortSizeField.setBounds(211, 192, 39, 21);
        mainPanel.add(shortSizeField);
        
        JLabel label_17 = new JLabel("Sock Size:");
        label_17.setBounds(276, 192, 104, 21);
        mainPanel.add(label_17);
        sockSizeField = new JTextField();
        sockSizeField.setBounds(334, 192, 46, 21);
        mainPanel.add(sockSizeField);
        
        JLabel label_18 = new JLabel("Medical Insurer:");
        label_18.setBounds(0, 223, 162, 21);
        mainPanel.add(label_18);
        medicalInsurerField = new JTextField();
        medicalInsurerField.setBounds(102, 223, 148, 21);
        mainPanel.add(medicalInsurerField);
        
        JLabel label_19 = new JLabel("Paid:");
        label_19.setBounds(390, 192, 86, 21);
        mainPanel.add(label_19);
        medicalConcernsField = new JTextField();
        medicalConcernsField.setBounds(431, 192, 45, 21);
        mainPanel.add(medicalConcernsField);
        
        //adult 
        JLabel label_20 = new JLabel("Last Name:");
        label_20.setBounds(0, 433, 357, 21);
        mainPanel.add(label_20);
        adultLastNameField = new JTextField();
        adultLastNameField.setBounds(357, 433, 357, 21);
        mainPanel.add(adultLastNameField);
        
        JLabel label_21 = new JLabel("First Name:");
        label_21.setBounds(0, 454, 357, 21);
        mainPanel.add(label_21);
        adultFirstNameField = new JTextField();
        adultFirstNameField.setBounds(357, 454, 357, 21);
        mainPanel.add(adultFirstNameField);
        
        JLabel label_22 = new JLabel("Phone 1:");
        label_22.setBounds(0, 475, 357, 21);
        mainPanel.add(label_22);
        adultPhone1Field = new JTextField();
        adultPhone1Field.setBounds(357, 475, 357, 21);
        mainPanel.add(adultPhone1Field);
        
        JLabel label_23 = new JLabel("Phone 2:");
        label_23.setBounds(0, 496, 357, 21);
        mainPanel.add(label_23);
        adultPhone2Field = new JTextField();
        adultPhone2Field.setBounds(357, 496, 357, 21);
        mainPanel.add(adultPhone2Field);
        
        JLabel label_24 = new JLabel("Email:");
        label_24.setBounds(0, 517, 357, 21);
        mainPanel.add(label_24);
        adultEmailField = new JTextField();
        adultEmailField.setBounds(357, 517, 357, 21);
        mainPanel.add(adultEmailField);
        

        submitButton = new JButton("Submit");
        submitButton.setBounds(357, 538, 357, 21);
        submitButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	    try {
        	        int playerId = Integer.parseInt(playerIdField.getText());
        	        String firstName = firstNameField.getText();
        	        String lastName = lastNameField.getText();
        	        String dateString = birthdateField.getText();
        	        String gender = (String) genderBox.getSelectedItem();
        	        int skillLevel = Integer.parseInt(skillLevelField.getText());
        	        int seasonsPlayed = Integer.parseInt(seasonsPlayedField.getText());
        	        boolean isRegistered = isRegisteredBox.isSelected();
        	        boolean isAssigned = isAssignedBox.isSelected();

        	        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
        	            JOptionPane.showMessageDialog(null, "First name should only contain letters and cannot be empty.");
        	            return;
        	        }

        	        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
        	            JOptionPane.showMessageDialog(null, "Last name should only contain letters and cannot be empty.");
        	            return;
        	        }

        	        if (!dateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
        	            JOptionPane.showMessageDialog(null, "Please enter a valid date in the format yyyy-MM-dd.");
        	            return;
        	        }

        	        Date birthdate = Date.valueOf(dateString);

        	        AddPlayerController.addPlayer(playerId, firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, isRegistered, isAssigned);

        	        JOptionPane.showMessageDialog(null, "Player added successfully.");

        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(null, "Please enter valid numbers for Player ID, Skill Level, and Seasons Played.");
        	    } catch (IllegalArgumentException ex) {
        	        JOptionPane.showMessageDialog(null, "Please enter a valid date in the format yyyy-MM-dd.");
        	    }
        	}
        });

        JLabel label_25 = new JLabel();
        label_25.setBounds(0, 538, 357, 21);
        mainPanel.add(label_25);
        mainPanel.add(submitButton);

        getContentPane().add(mainPanel);
    }

    
}