package interfaceGui;


//Interface for editing player information already present
//Add player form but with current player information 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import soccerManagment.DatabaseConnection;
import soccerManagment.UpdatePlayerController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import interfaceGui.PlayerSearch;

public class EditPlayerForm extends JFrame {

    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> genderBox;
    private JTextField birthdateField;
    private JTextField skillLevelField;
    private JTextField seasonsPlayedField;
    private JTextField assignedField;
    private JTextField registeredfield;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipCodeField;
    private JTextField carPoolField;
    private JTextField leagueField;
    private JComboBox<String> jerseySizeBox;
    private JComboBox<String> shortSizeBox;
    private JComboBox<String> sockSizeBox;

    private JComboBox<String> paidBox;
    private JTextField medicalInsurerField;
    private JTextArea medicalConcernsArea;
    
    
    private JTextField adultLastNameField;
    private JTextField adultFirstNameField;
    private JTextField adultPhone1Field;
    private JTextField adultPhone2Field;
    private JTextField adultEmailField;
    private JTextField secondAdultLastNameField;
    private JTextField secondAdultFirstNameField;
    private JTextField secondAdultPhone1Field;
    private JTextField secondAdultPhone2Field;
    private JTextField secondAdultEmailField;
    private int playerId;
    private JTextField teamId;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private JTextField teamIdField;
   

    public EditPlayerForm(int playerId) {
        setTitle("Edit Player");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 844, 671);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        this.playerId = playerId;        

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(10, 10, 80, 25);
        contentPane.add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(100, 10, 120, 25);
        contentPane.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(10, 40, 80, 25);
        contentPane.add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(100, 40, 120, 25);
        contentPane.add(lastNameField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(10, 70, 80, 25);
        contentPane.add(genderLabel);

        String[] genders = {"M", "F"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(100, 70, 50, 25);
        contentPane.add(genderBox);

        JLabel birthdateLabel = new JLabel("Birthdate:");
        birthdateLabel.setBounds(10, 100, 80, 25);
        contentPane.add(birthdateLabel);

        birthdateField = new JTextField();
        birthdateField.setBounds(100, 100, 120, 25);
        contentPane.add(birthdateField);

        JLabel skillLevelLabel = new JLabel("Skill Level:");
        skillLevelLabel.setBounds(10, 130, 80, 25);
        contentPane.add(skillLevelLabel);

        skillLevelField = new JTextField();
        skillLevelField.setBounds(100, 130, 50, 25);
        contentPane.add(skillLevelField);

        JLabel seasonsPlayedLabel = new JLabel("Seasons Played:");
        seasonsPlayedLabel.setBounds(10, 160, 120, 25);
        contentPane.add(seasonsPlayedLabel);

        seasonsPlayedField = new JTextField();
        seasonsPlayedField.setBounds(100, 165, 50, 25);
        contentPane.add(seasonsPlayedField);

        JLabel assignedLabel = new JLabel("Assigned:");
        assignedLabel.setBounds(10, 190, 80, 25);
        contentPane.add(assignedLabel);
        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 257, 80, 25);
        contentPane.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(100, 257, 120, 25);
        contentPane.add(addressField);

        assignedField = new JTextField();
        assignedField.setBounds(100, 190, 50, 25);
        contentPane.add(assignedField);
        
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(10, 292, 80, 25);
        contentPane.add(cityLabel);

        cityField = new JTextField();
        cityField.setBounds(100, 292, 120, 25);
        contentPane.add(cityField);
        
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setBounds(10, 327, 80, 25);
        contentPane.add(stateLabel);

        stateField = new JTextField();
        stateField.setBounds(100, 327, 120, 25);
        contentPane.add(stateField);

        // Zip Code
        JLabel zipCodeLabel = new JLabel("Zip Code:");
        zipCodeLabel.setBounds(10, 362, 80, 25);
        contentPane.add(zipCodeLabel);

        zipCodeField = new JTextField();
        zipCodeField.setBounds(100, 362, 120, 25);
        contentPane.add(zipCodeField);

        // Car Pool
        JLabel carPoolLabel = new JLabel("Car Pool:");
        carPoolLabel.setBounds(10, 396, 80, 25);
        contentPane.add(carPoolLabel);

        carPoolField = new JTextField();
        carPoolField.setBounds(100, 396, 120, 25);
        contentPane.add(carPoolField);

        // League
        JLabel leagueLabel = new JLabel("League:");
        leagueLabel.setBounds(10, 431, 80, 25);
        contentPane.add(leagueLabel);

        leagueField = new JTextField();
        leagueField.setBounds(100, 431, 120, 25);
        contentPane.add(leagueField);

        // Jersey Size
        JLabel jerseySizeLabel = new JLabel("Jersey Size:");
        jerseySizeLabel.setBounds(10, 466, 80, 25);
        contentPane.add(jerseySizeLabel);

        String[] jerseySizes = {"S", "M", "L"};
        jerseySizeBox = new JComboBox<>(jerseySizes);
        jerseySizeBox.setBounds(100, 466, 50, 25);
        contentPane.add(jerseySizeBox);

        // Short Size
        JLabel shortSizeLabel = new JLabel("Short Size:");
        shortSizeLabel.setBounds(10, 501, 80, 25);
        contentPane.add(shortSizeLabel);

        String[] shortSizes = {"S", "M", "L"};
        shortSizeBox = new JComboBox<>(shortSizes);
        shortSizeBox.setBounds(100, 501, 50, 25);
        contentPane.add(shortSizeBox);

        // Sock Size
        JLabel sockSizeLabel = new JLabel("Sock Size:");
        sockSizeLabel.setBounds(10, 536, 80, 25);
        contentPane.add(sockSizeLabel);

        String[] sockSizes = {"S", "M", "L"};
        sockSizeBox = new JComboBox<>(sockSizes);
        sockSizeBox.setBounds(100, 536, 50, 25);
        contentPane.add(sockSizeBox);
        
     // Paid
        JLabel paidLabel = new JLabel("Paid:");
        paidLabel.setBounds(10, 571, 80, 25);
        contentPane.add(paidLabel);
        //HERE
        String[] paidOptions = {"Yes", "No"};
        paidBox = new JComboBox<>(paidOptions);
        paidBox.setBounds(100, 571, 60, 25);
        contentPane.add(paidBox);

        // Medical Insurer
        JLabel medicalInsurerLabel = new JLabel("Medical Insurer:");
        medicalInsurerLabel.setBounds(230, 315, 120, 25);
        contentPane.add(medicalInsurerLabel);

        medicalInsurerField = new JTextField();
        medicalInsurerField.setBounds(350, 315, 120, 25);
        contentPane.add(medicalInsurerField);

        // Medical Concerns
        JLabel medicalConcernsLabel = new JLabel("Medical Concerns:");
        medicalConcernsLabel.setBounds(230, 350, 120, 25);
        contentPane.add(medicalConcernsLabel);

        medicalConcernsArea = new JTextArea();
        medicalConcernsArea.setBounds(349, 350, 200, 75);
        contentPane.add(medicalConcernsArea);
        
        JLabel adultLastNameLabel = new JLabel("Adult Last Name:");
        adultLastNameLabel.setBounds(230, 10, 120, 25);
        contentPane.add(adultLastNameLabel);

        adultLastNameField = new JTextField();
        adultLastNameField.setBounds(350, 10, 120, 25);
        contentPane.add(adultLastNameField);

        JLabel adultFirstNameLabel = new JLabel("Adult First Name:");
        adultFirstNameLabel.setBounds(230, 40, 120, 25);
        contentPane.add(adultFirstNameLabel);

        adultFirstNameField = new JTextField();
        adultFirstNameField.setBounds(350, 40, 120, 25);
        contentPane.add(adultFirstNameField);

        JLabel adultPhone1Label = new JLabel("Adult Phone 1:");
        adultPhone1Label.setBounds(230, 70, 120, 25);
        contentPane.add(adultPhone1Label);

        adultPhone1Field = new JTextField();
        adultPhone1Field.setBounds(350, 70, 120, 25);
        contentPane.add(adultPhone1Field);

        JLabel adultPhone2Label = new JLabel("Adult Phone 2:");
        adultPhone2Label.setBounds(230, 100, 120, 25);
        contentPane.add(adultPhone2Label);

        adultPhone2Field = new JTextField();
        adultPhone2Field.setBounds(350, 100, 120, 25);
        contentPane.add(adultPhone2Field);

        JLabel adultEmailLabel = new JLabel("Adult Email:");
        adultEmailLabel.setBounds(230, 130, 120, 25);
        contentPane.add(adultEmailLabel);

        adultEmailField = new JTextField();
        adultEmailField.setBounds(350, 130, 120, 25);
        contentPane.add(adultEmailField);

        JLabel secondAdultLastNameLabel = new JLabel("2nd Adult Last Name:");
        secondAdultLastNameLabel.setBounds(230, 160, 120, 25);
        contentPane.add(secondAdultLastNameLabel);

        secondAdultLastNameField = new JTextField();
        secondAdultLastNameField.setBounds(350, 160, 120, 25);
        contentPane.add(secondAdultLastNameField);

        JLabel secondAdultFirstNameLabel = new JLabel("2nd Adult First Name:");
        secondAdultFirstNameLabel.setBounds(230, 190, 120, 25);
        contentPane.add(secondAdultFirstNameLabel);

        secondAdultFirstNameField = new JTextField();
        secondAdultFirstNameField.setBounds(350, 190, 120, 25);
        contentPane.add(secondAdultFirstNameField);

        JLabel secondAdultPhone1Label = new JLabel("2nd Adult Phone 1:");
        secondAdultPhone1Label.setBounds(230, 220, 120, 25);
        contentPane.add(secondAdultPhone1Label);

        secondAdultPhone1Field = new JTextField();
        secondAdultPhone1Field.setBounds(350, 220, 120, 25);
        contentPane.add(secondAdultPhone1Field);

        JLabel secondAdultPhone2Label = new JLabel("2nd Adult Phone 2:");
        secondAdultPhone2Label.setBounds(230, 250, 120, 25);
        contentPane.add(secondAdultPhone2Label);

        secondAdultPhone2Field = new JTextField();
        secondAdultPhone2Field.setBounds(350, 250, 120, 25);
        contentPane.add(secondAdultPhone2Field);

        JLabel secondAdultEmailLabel = new JLabel("2nd Adult Email:");
        secondAdultEmailLabel.setBounds(230, 285, 120, 25);
        contentPane.add(secondAdultEmailLabel);

        secondAdultEmailField = new JTextField();
        secondAdultEmailField.setBounds(350, 285, 120, 25);
        contentPane.add(secondAdultEmailField);
        

        JLabel teamIdLabel = new JLabel("TeamID:");
        teamIdLabel.setBounds(230, 482, 120, 25);
        contentPane.add(teamIdLabel);
        
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(527, 536, 80, 25);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdatePlayerController.updatePlayerData(
                    playerId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    (String) genderBox.getSelectedItem(),
                    birthdateField.getText(),
                    Integer.parseInt(skillLevelField.getText()),
                    seasonsPlayedField.getText(),
                    assignedField.getText(),
                    registeredfield.getText(), 
                    addressField.getText(), 
                    cityField.getText(), 
                    stateField.getText(),                     
                    Integer.parseInt(zipCodeField.getText()), 
                    Integer.parseInt(carPoolField.getText()),
                    leagueField.getText(),                     
                    (String) jerseySizeBox.getSelectedItem(),
                    (String) shortSizeBox.getSelectedItem(), 
                    (String) sockSizeBox.getSelectedItem(), 
                    (String) paidBox.getSelectedItem(), 
                    medicalInsurerField.getText(), 
                    medicalConcernsArea.getText(), 
                    adultFirstNameField.getText(), 
                    adultLastNameField.getText(), 
                    adultPhone1Field.getText(), 
                    adultPhone2Field.getText(),
                    adultEmailField.getText(), 
                    secondAdultFirstNameField.getText(),
                    secondAdultLastNameField.getText(),
                    secondAdultPhone1Field.getText(), 
                    secondAdultPhone2Field.getText(), 
                    secondAdultEmailField.getText(),
                    Integer.parseInt(teamIdField.getText())

                    
                );
                dispose();
            }
        });
        
        
        contentPane.add(saveButton);
        
        JLabel lblRegistered = new JLabel("Registered:");
        lblRegistered.setBounds(10, 223, 80, 25);
        contentPane.add(lblRegistered);
        
        registeredfield = new JTextField();
        registeredfield.setBounds(100, 225, 50, 25);
        contentPane.add(registeredfield);
        
        teamIdField = new JTextField();
        teamIdField.setBounds(350, 485, 120, 25);
        contentPane.add(teamIdField);
        
        loadPlayerData();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                PlayerSearch playerSearch = new PlayerSearch();
                playerSearch.setVisible(true);
            }
        });

    }
    

    private void loadPlayerData() {
        try {
            connection = DatabaseConnection.openConnection();
            statement = connection.prepareStatement("SELECT * FROM PlayerInformation WHERE PlayerId=?");
            statement.setInt(1, playerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                firstNameField.setText(resultSet.getString("FirstName"));
                lastNameField.setText(resultSet.getString("LastName"));
                genderBox.setSelectedItem(resultSet.getString("Gender"));
                Date birthdate = resultSet.getDate("Birthdate");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedBirthdate = dateFormat.format(birthdate);
                birthdateField.setText(formattedBirthdate);
                skillLevelField.setText(String.valueOf(resultSet.getInt("SkillLevel")));
                seasonsPlayedField.setText(String.valueOf(resultSet.getInt("SeasonsPlayed")));
                assignedField.setText(resultSet.getString("Assigned"));
                registeredfield.setText(resultSet.getString("Registered"));
                
                addressField.setText(resultSet.getString("Address"));
                cityField.setText(resultSet.getString("City"));
                stateField.setText(resultSet.getString("State"));
                zipCodeField.setText(resultSet.getString("ZipCode"));
                carPoolField.setText(resultSet.getString("CarPool"));
                leagueField.setText(resultSet.getString("League"));
                jerseySizeBox.setSelectedItem(resultSet.getString("JerseySize"));
                shortSizeBox.setSelectedItem(resultSet.getString("ShortSize"));
                sockSizeBox.setSelectedItem(resultSet.getString("SockSize"));
                paidBox.setSelectedItem(resultSet.getString("Paid"));
                medicalInsurerField.setText(resultSet.getString("MedicalInsurance"));
                medicalConcernsArea.setText(resultSet.getString("MedicalConcerns"));
                
                adultLastNameField.setText(resultSet.getString("AdultLastName"));
                adultFirstNameField.setText(resultSet.getString("AdultFirstName"));
                adultPhone1Field.setText(resultSet.getString("AdultPhone1"));
                adultPhone2Field.setText(resultSet.getString("AdultPhone2"));
                adultEmailField.setText(resultSet.getString("AdultEmail"));
                secondAdultLastNameField.setText(resultSet.getString("SecondAdultLastName"));
                secondAdultFirstNameField.setText(resultSet.getString("SecondAdultFirstName"));
                secondAdultPhone1Field.setText(resultSet.getString("SecondAdultPhone1"));
                secondAdultPhone2Field.setText(resultSet.getString("SecondAdultPhone2"));
                secondAdultEmailField.setText(resultSet.getString("SecondAdultEmail"));
                teamIdField.setText(String.valueOf(resultSet.getInt("TeamId")));

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
    
}
