package interfaceGui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import soccerManagment.AddPlayerController;
import soccerManagment.PlayerValidationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

public class AddPlayerForm extends JPanel {
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
    private JTextField teamIdField;
    private JComboBox<String> jerseySizeBox;
    private JComboBox<String> shortSizeBox;
    private JComboBox<String> sockSizeBox;

    private JComboBox<String> paidBox;
    private JTextField medicalInsurerField;
    private JTextArea medicalConcernsArea;


    //parent/guardian information
    private JTextField adultLastNameField;
    private JTextField adultFirstNameField;
    private JTextField adultPhone1Field;
    private JTextField adultPhone2Field;
    private JTextField adultEmailField;
    private JTextField textField;
    private JLabel label;
    private JLabel label_26;
    private JLabel label_27;
    private JLabel label_28;
    private JLabel label_29;
    private JTextField SecondAdultLastNameField;
    private JTextField SecondAdultFirstNameField;
    private JTextField SecondAdultPhone1Field;
    private JTextField SecondAdultPhone2Field;
    private JTextField SecondAdultEmailField;
    private JLabel label_30;
    private JTextArea textArea;

    public AddPlayerForm() {
        setLayout(null);
        setSize(721, 407);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 719, 400);
        add(mainPanel);

        ///

        JLabel label_1 = new JLabel("First Name:");
        label_1.setBounds(4, 8, 64, 21);
        mainPanel.add(label_1);
        firstNameField = new JTextField();
        firstNameField.setBounds(64, 8, 145, 21);
        mainPanel.add(firstNameField);

        JLabel label_2 = new JLabel("Last Name:");
        label_2.setBounds(219, 8, 81, 21);
        mainPanel.add(label_2);
        lastNameField = new JTextField();
        lastNameField.setBounds(290, 8, 148, 21);
        mainPanel.add(lastNameField);

        JLabel label_3 = new JLabel("Birthdate (yyyy-MM-dd):");
        label_3.setBounds(442, 8, 176, 21);
        mainPanel.add(label_3);
        birthdateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        birthdateField.setBounds(576, 8, 133, 21);
        mainPanel.add(birthdateField);

        JLabel label_4 = new JLabel("Gender:");
        label_4.setBounds(4, 31, 54, 21);
        mainPanel.add(label_4);
        String[] genders = {"M", "F"};
        genderBox = new JComboBox<>();
        genderBox.setBounds(64, 31, 74, 21);
        DefaultComboBoxModel<String> genderModel = new DefaultComboBoxModel<>(genders);
        genderBox.setModel(genderModel);
        mainPanel.add(genderBox);

        JLabel label_5 = new JLabel("Skill Level:");
        label_5.setBounds(148, 31, 81, 21);
        mainPanel.add(label_5);
        skillLevelField = new JTextField();
        skillLevelField.setBounds(215, 31, 47, 21);
        mainPanel.add(skillLevelField);

        JLabel label_6 = new JLabel("Seasons Played:");
        label_6.setBounds(276, 31, 113, 21);
        mainPanel.add(label_6);
        seasonsPlayedField = new JTextField();
        seasonsPlayedField.setBounds(399, 31, 39, 21);
        mainPanel.add(seasonsPlayedField);

        JLabel label_7 = new JLabel("Registered:");
        label_7.setBounds(442, 31, 59, 21);
        mainPanel.add(label_7);
        isRegisteredBox = new JCheckBox();
        isRegisteredBox.setBounds(501, 35, 39, 21);
        mainPanel.add(isRegisteredBox);

        JLabel label_8 = new JLabel("Assigned:");
        label_8.setBounds(564, 31, 54, 21);
        mainPanel.add(label_8);
        isAssignedBox = new JCheckBox();
        isAssignedBox.setBounds(624, 35, 39, 21);
        mainPanel.add(isAssignedBox);
        
        JLabel label_9 = new JLabel("Address:");
        label_9.setBounds(4, 62, 54, 21);
        mainPanel.add(label_9);
        addressField = new JTextField();
        addressField.setBounds(64, 62, 186, 21);
        mainPanel.add(addressField);

        JLabel label_10 = new JLabel("City:");
        label_10.setBounds(260, 62, 31, 21);
        mainPanel.add(label_10);
        cityField = new JTextField();
        cityField.setBounds(286, 62, 73, 21);
        mainPanel.add(cityField);
        
        JLabel label_11 = new JLabel("State:");
        label_11.setBounds(367, 62, 86, 21);
        mainPanel.add(label_11);
        stateField = new JTextField();
        stateField.setBounds(407, 62, 45, 21);
        mainPanel.add(stateField);
        
        JLabel label_12 = new JLabel("Zip:");
        label_12.setBounds(470, 62, 31, 21);
        mainPanel.add(label_12);
        zipCodeField = new JTextField();
        zipCodeField.setBounds(501, 62, 74, 21);
        mainPanel.add(zipCodeField);
        
        JLabel label_13 = new JLabel("Car Pool:");
        label_13.setBounds(4, 86, 138, 21);
        mainPanel.add(label_13);
        carPoolField = new JTextField();
        carPoolField.setBounds(64, 86, 74, 21);
        mainPanel.add(carPoolField);
        
        
        
        JLabel label_14 = new JLabel("League:");
        label_14.setBounds(4, 117, 99, 21);
        mainPanel.add(label_14);
        leagueField = new JTextField();
        leagueField.setBounds(64, 117, 39, 21);
        mainPanel.add(leagueField);
        
        JLabel label_50 = new JLabel("Team:");
        label_50.setBounds(148, 86, 99, 21);
        mainPanel.add(label_50);
        teamIdField = new JTextField();
        teamIdField.setBounds(192, 86, 39, 21);
        mainPanel.add(teamIdField);
        
        //////
        JLabel label_15 = new JLabel("Jersey Size:");
        label_15.setBounds(260, 86, 73, 21);
        mainPanel.add(label_15);
        String[] jerseySize = {"S","M", "L"};
        jerseySizeBox = new JComboBox<>();
        jerseySizeBox.setBounds(333, 86, 74, 21);
        DefaultComboBoxModel<String> jerseySizeModel = new DefaultComboBoxModel<>(jerseySize);
        jerseySizeBox.setModel(jerseySizeModel);
        mainPanel.add(jerseySizeBox);
        //////
        JLabel label_16 = new JLabel("Short Size:");
        label_16.setBounds(417, 86, 59, 21);
        mainPanel.add(label_16);
        String[] shortSize = {"S","M", "L"};
        shortSizeBox = new JComboBox<>();
        shortSizeBox.setBounds(480, 86, 39, 21);
        DefaultComboBoxModel<String> shortSizeModel = new DefaultComboBoxModel<>(shortSize);
        shortSizeBox.setModel(shortSizeModel);
        mainPanel.add(shortSizeBox);
        //////
        
        JLabel label_17 = new JLabel("Sock Size:");
        label_17.setBounds(526, 86, 104, 21);
        mainPanel.add(label_17);
        String[] sockSize = {"S","M", "L"};
        sockSizeBox = new JComboBox<>();
        sockSizeBox.setBounds(584, 86, 46, 21);
        DefaultComboBoxModel<String> sockSizeModel = new DefaultComboBoxModel<>(sockSize);
        sockSizeBox.setModel(sockSizeModel);
        mainPanel.add(sockSizeBox);
        //////
        

        JLabel label_19 = new JLabel("Paid:");
        label_19.setBounds(643, 62, 86, 21);
        mainPanel.add(label_19);
        String[] paid = {"Yes", "No"};
        paidBox = new JComboBox<>();
        paidBox.setBounds(643, 80, 66, 32);
        DefaultComboBoxModel<String> paidModel = new DefaultComboBoxModel<>(paid);
        paidBox.setModel(paidModel);
        mainPanel.add(paidBox);
        //////
        
        
        JLabel label_18 = new JLabel("Medical Insurer:");
        label_18.setBounds(4, 143, 77, 21);
        mainPanel.add(label_18);
        medicalInsurerField = new JTextField();
        medicalInsurerField.setBounds(102, 143, 148, 21);
        mainPanel.add(medicalInsurerField);
        
        JLabel lblMedicalConcerns = new JLabel("Medical Concerns:");
        lblMedicalConcerns.setBounds(149, 117, 113, 21);
        mainPanel.add(lblMedicalConcerns);
        
        medicalConcernsArea = new JTextArea();
        medicalConcernsArea.setBounds(265, 115, 444, 89);
        mainPanel.add(medicalConcernsArea);
        
        
        
        
        //adult 
        JLabel label_20 = new JLabel("Last Name:");
        label_20.setBounds(4, 208, 77, 21);
        mainPanel.add(label_20);
        adultLastNameField = new JTextField();
        adultLastNameField.setBounds(64, 208, 176, 21);
        mainPanel.add(adultLastNameField);
        
        JLabel label_21 = new JLabel("First Name:");
        label_21.setBounds(4, 239, 64, 21);
        mainPanel.add(label_21);
        adultFirstNameField = new JTextField();
        adultFirstNameField.setBounds(64, 241, 176, 21);
        mainPanel.add(adultFirstNameField);
        
        JLabel label_22 = new JLabel("Phone 1:");
        label_22.setBounds(4, 270, 58, 21);
        mainPanel.add(label_22);
        adultPhone1Field = new JTextField();
        adultPhone1Field.setBounds(64, 270, 176, 21);
        mainPanel.add(adultPhone1Field);
        
        JLabel label_23 = new JLabel("Phone 2:");
        label_23.setBounds(4, 301, 54, 21);
        mainPanel.add(label_23);
        adultPhone2Field = new JTextField();
        adultPhone2Field.setBounds(64, 301, 176, 21);
        mainPanel.add(adultPhone2Field);
        
        JLabel label_24 = new JLabel("Email:");
        label_24.setBounds(4, 332, 39, 21);
        mainPanel.add(label_24);
        adultEmailField = new JTextField();
        adultEmailField.setBounds(64, 332, 176, 21);
        mainPanel.add(adultEmailField);
        

        submitButton = new JButton("Submit");
        submitButton.setBounds(2, 369, 248, 21);
      
            
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // First, get all the data from the form fields
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String dateString = birthdateField.getText();
                    String gender = (String) genderBox.getSelectedItem();
                    int skillLevel = safeParseInt(skillLevelField.getText(), 0); 
                    int seasonsPlayed = safeParseInt(seasonsPlayedField.getText(), 0);
                    boolean isRegistered = isRegisteredBox.isSelected();
                    boolean isAssigned = isAssignedBox.isSelected();
                    String address = addressField.getText();
                    String city = cityField.getText();
                    String state = stateField.getText();
                    int zipCode = safeParseInt(zipCodeField.getText(), 0);
                    int carPool = safeParseInt(carPoolField.getText(), 0);
                    String league = leagueField.getText();
                    String jerseySize = (String) jerseySizeBox.getSelectedItem();
                    String shortsSize = (String) shortSizeBox.getSelectedItem();
                    String sockSize = (String) sockSizeBox.getSelectedItem();
                    String paid = (String) paidBox.getSelectedItem();
                    String medicalInsurance = medicalInsurerField.getText();
                    String medicalConcerns = medicalConcernsArea.getText();
                    String adultLastName = adultLastNameField.getText();
                    String adultFirstName = adultFirstNameField.getText();
                    String adultPhone1 = adultPhone1Field.getText();
                    String adultPhone2 = adultPhone2Field.getText();
                    String adultEmail = adultEmailField.getText();
                    String secondAdultLastName = SecondAdultLastNameField.getText();
                    String secondAdultFirstName = SecondAdultFirstNameField.getText();
                    String secondAdultPhone1 = SecondAdultPhone1Field.getText();
                    String secondAdultPhone2 = SecondAdultPhone2Field.getText();
                    String secondAdultEmail = SecondAdultEmailField.getText();
                    int teamID = safeParseInt(teamIdField.getText(), 0);


                    if(dateString == null || dateString.trim().isEmpty()) {
                        dateString = "1000-10-10";
                    }


                    // Call the PlayerValidator.validatePlayerData method
                    TeamWindow teamWindow = new TeamWindow();

                 // Get the set of team IDs from TeamWindow
                 Set<Integer> teamIds = teamWindow.getTeamIds();

                 // Call the PlayerValidator.validatePlayerData method with teamIds as the first argument
                 List<String> errors = PlayerValidationController.validatePlayerData(teamIds, firstName, lastName, Date.valueOf(dateString), gender, skillLevel, seasonsPlayed, isRegistered, isAssigned, address, city, state, zipCode, carPool, league, jerseySize, shortsSize, sockSize, paid, medicalInsurance, medicalConcerns, adultLastName, adultFirstName, adultPhone1, adultPhone2, adultEmail, secondAdultLastName, secondAdultFirstName, secondAdultPhone1, secondAdultPhone2, secondAdultEmail, teamID);
                    // Check if there are any errors
                    if (errors.isEmpty()) {
                        // If there are no errors, parse the date and call the AddPlayerController
                        Date birthdate = Date.valueOf(dateString);
                        
                        AddPlayerController.addPlayer(firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, isRegistered, isAssigned, address, city, state, zipCode, carPool, league, jerseySize, shortsSize, sockSize, paid, medicalInsurance, medicalConcerns, adultLastName, adultFirstName, adultPhone1, adultPhone2, adultEmail, secondAdultLastName, secondAdultFirstName, secondAdultPhone1, secondAdultPhone2, secondAdultEmail, teamID);
                        JOptionPane.showMessageDialog(null, "Player added successfully.");
                    } else {
                        
                        String errorMessage = String.join("\n", errors);
                        JOptionPane.showMessageDialog(null, errorMessage);
                    }
                }

                private int safeParseInt(String input, int defaultValue) {
                    try {
                        return Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        return defaultValue;
                    }
                }
            });

        JLabel label_25 = new JLabel();
        label_25.setBounds(0, 538, 357, 21);
        mainPanel.add(label_25);
        mainPanel.add(submitButton);
        
        label = new JLabel("Last Name:");
        label.setBounds(260, 208, 85, 21);
        mainPanel.add(label);
        
        label_26 = new JLabel("First Name:");
        label_26.setBounds(260, 241, 64, 21);
        mainPanel.add(label_26);
        
        label_27 = new JLabel("Phone 1:");
        label_27.setBounds(260, 272, 58, 21);
        mainPanel.add(label_27);
        
        label_28 = new JLabel("Phone 2:");
        label_28.setBounds(260, 303, 54, 21);
        mainPanel.add(label_28);
        
        label_29 = new JLabel("Email:");
        label_29.setBounds(261, 332, 39, 21);
        mainPanel.add(label_29);
        
        SecondAdultLastNameField = new JTextField();
        SecondAdultLastNameField.setBounds(325, 208, 176, 21);
        mainPanel.add(SecondAdultLastNameField);
        
        SecondAdultFirstNameField = new JTextField();
        SecondAdultFirstNameField.setBounds(325, 242, 176, 21);
        mainPanel.add(SecondAdultFirstNameField);
        
        SecondAdultPhone1Field = new JTextField();
        SecondAdultPhone1Field.setBounds(325, 273, 176, 21);
        mainPanel.add(SecondAdultPhone1Field);
        
        SecondAdultPhone2Field = new JTextField();
        SecondAdultPhone2Field.setBounds(324, 304, 176, 21);
        mainPanel.add(SecondAdultPhone2Field);
        
        SecondAdultEmailField = new JTextField();
        SecondAdultEmailField.setBounds(325, 333, 176, 21);
        mainPanel.add(SecondAdultEmailField);
        
        label_30 = new JLabel("Emergancy Contact Information");
        label_30.setFont(new Font("Tahoma", Font.PLAIN, 15));
        label_30.setBounds(4, 174, 315, 21);
        mainPanel.add(label_30);
  
    }
}