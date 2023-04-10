package interfaceGui;
//form for edit player
//seperate out save action
//add filters to save action 
//database doesn't actually save information that is not permitted in columns
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import soccerManagment.DatabaseConnection;

public class EditPlayerForm extends JFrame {

  private JPanel contentPane;
  private JTextField firstNameField;
  private JTextField lastNameField;
  private JComboBox<String> genderBox;
  private JTextField birthdateField;
  private JTextField skillLevelField;
  private JTextField seasonsPlayedField;
  private JTextField assignedField;
  private int playerId;
  
  private Connection connection;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public EditPlayerForm(int playerId) {
      setTitle("Edit Player");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(100, 100, 400, 400);
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
      seasonsPlayedField.setBounds(120, 160, 50, 25);
      contentPane.add(seasonsPlayedField);

      JLabel assignedLabel = new JLabel("Assigned:");
      assignedLabel.setBounds(10, 190, 80, 25);
      contentPane.add(assignedLabel);

      assignedField = new JTextField();
      assignedField.setBounds(100, 190, 50, 25);
      contentPane.add(assignedField);
      
      JButton saveButton = new JButton("Save");
      saveButton.setBounds(150, 250, 80, 25);
      saveButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              try {
                  connection = DatabaseConnection.openConnection();
                  statement = connection.prepareStatement("UPDATE PlayerInformation SET FirstName=?, LastName=?, Gender=?, Birthdate=?, SkillLevel=?, SeasonsPlayed=?, Assigned=? WHERE PlayerId=?");
                  statement.setString(1, firstNameField.getText());
                  statement.setString(2, lastNameField.getText());
                  statement.setString(3, (String)genderBox.getSelectedItem());
                  statement.setString(4, birthdateField.getText());
                  statement.setInt(5, Integer.parseInt(skillLevelField.getText()));
                  statement.setString(6, seasonsPlayedField.getText());
                  statement.setString(7, assignedField.getText());
                  statement.setInt(8, playerId);
                  statement.executeUpdate();
                  JOptionPane.showMessageDialog(null, "Player information updated successfully!");
                  dispose();
              } catch (SQLException ex) {
                  ex.printStackTrace();
              } finally {
                  try {
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
      contentPane.add(saveButton);
      
      try {
    	    connection = DatabaseConnection.openConnection();
    	    statement = connection.prepareStatement("SELECT * FROM PlayerInformation WHERE PlayerId = ?");
    	    statement.setInt(1, playerId);
    	    resultSet = statement.executeQuery();

    	    if (resultSet.next()) {
    	        String firstName = resultSet.getString("FirstName");
    	        String lastName = resultSet.getString("LastName");
    	        String gender = resultSet.getString("Gender");
    	        String birthdate = resultSet.getString("Birthdate");
    	        int skillLevel = resultSet.getInt("SkillLevel");
    	        String seasonsPlayed = resultSet.getString("SeasonsPlayed");
    	        String registered = resultSet.getString("Registered");
    	        String assigned = resultSet.getString("Assigned");

    	        // Populate the fields with the player's current information
    	        firstNameField.setText(firstName);
    	        lastNameField.setText(lastName);
    	        genderBox.setSelectedItem(gender);
    	        birthdateField.setText(birthdate);
    	        skillLevelField.setText(Integer.toString(skillLevel));
    	        seasonsPlayedField.setText(seasonsPlayed);
    	        assignedField.setText(assigned);
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