package interfaceGui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import soccerManagment.AddPlayerAction;
import soccerManagment.DatabaseConnection;
import soccerManagment.PlayerInformation;

public class PlayersList extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private ArrayList<PlayerInformation> playersList;
    private JTextField txtPlayerId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtBirthdate;
    private JCheckBox chkMale;
    private JCheckBox chkFemale;
    private JTextField txtSkillLevel;
    private JTextField txtSeasonsPlayed;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayersList frame = new PlayersList();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PlayersList() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        setTitle("Player Information");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Create table
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 664, 239);
        contentPane.add(scrollPane);

        // Set up the table model a for table
        model = new DefaultTableModel(new String[] { "Player ID", "First Name", "Last Name", "Birthdate", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned" }, 0);
        table.setModel(model);

        // Add items to table
        try {
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerInformation");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int playerId = resultSet.getInt("PlayerId");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String birthdate = resultSet.getString("Birthdate");
                String gender = resultSet.getString("Gender");
                int skillLevel = resultSet.getInt("SkillLevel");
                int seasonsPlayed = resultSet.getInt("SeasonsPlayed");
                String registered = resultSet.getString("Registered");
                String assigned = resultSet.getString("Assigned");

                model.addRow(new Object[] { playerId, firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, registered, assigned });
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Inputs for new addition
        
        JLabel lblPlayerId = new JLabel("Player ID:");
        lblPlayerId.setBounds(20, 260, 70, 14);
        contentPane.add(lblPlayerId);
        
        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(100, 260, 70, 14);
        contentPane.add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(180, 260, 70, 14);
        contentPane.add(lblLastName);
        
        JLabel lblBirthdate = new JLabel("Birthdate:");
        lblBirthdate.setBounds(260, 260, 70, 14);
        contentPane.add(lblBirthdate);
        
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(369, 260, 70, 14);
        contentPane.add(lblGender);
        
        JLabel lblSkillLevel = new JLabel("Skill Level:");
        lblSkillLevel.setBounds(20, 310, 70, 14);
        contentPane.add(lblSkillLevel);
        
        JLabel lblSeasonsPlayed = new JLabel("Seasons Played:");
        lblSeasonsPlayed.setBounds(100, 310, 100, 14);
        contentPane.add(lblSeasonsPlayed);

        JCheckBox chkRegistered = new JCheckBox("Registered");
        chkRegistered.setBounds(185, 330, 90, 20);
        contentPane.add(chkRegistered);
        
        JCheckBox chkAssigned = new JCheckBox("Assigned");
        chkAssigned.setBounds(275, 330, 90, 20);
        contentPane.add(chkAssigned);

        txtPlayerId = new JTextField();
        txtPlayerId.setBounds(20, 280, 70, 20);
        contentPane.add(txtPlayerId);
        txtPlayerId.setColumns(10);

        txtFirstName = new JTextField();
        txtFirstName.setBounds(100, 280, 70, 20);
        contentPane.add(txtFirstName);
        txtFirstName.setColumns(10);

        txtLastName = new JTextField();
        txtLastName.setBounds(180, 280, 70, 20);
        contentPane.add(txtLastName);
        txtLastName.setColumns(10);
        
        txtBirthdate = new JTextField();
        txtBirthdate.setBounds(260, 280, 70, 20);
        contentPane.add(txtBirthdate);
        txtBirthdate.setColumns(10);
        
        chkMale = new JCheckBox("Male");
        chkMale.setBounds(336, 279, 60, 20);
        contentPane.add(chkMale);

        chkFemale = new JCheckBox("Female");
        chkFemale.setBounds(403, 280, 70, 20);
        contentPane.add(chkFemale);

        txtSkillLevel = new JTextField();
        txtSkillLevel.setBounds(20, 334, 60, 20);
        contentPane.add(txtSkillLevel);
        txtSkillLevel.setColumns(10);

        txtSeasonsPlayed = new JTextField();
        txtSeasonsPlayed.setBounds(100, 334, 70, 20);
        contentPane.add(txtSeasonsPlayed);
        txtSeasonsPlayed.setColumns(10);

     // Add player button
        JButton btnAddPlayer = new JButton("Add Player");
        btnAddPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int playerId = Integer.parseInt(txtPlayerId.getText());
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                java.sql.Date birthdate = java.sql.Date.valueOf(txtBirthdate.getText());
                String gender = "";

                if (chkMale.isSelected()) {
                    gender = "Male";
                } else if (chkFemale.isSelected()) {
                    gender = "Female";
                }

                int skillLevel = Integer.parseInt(txtSkillLevel.getText());
                int seasonsPlayed = Integer.parseInt(txtSeasonsPlayed.getText());
                boolean isRegistered = chkRegistered.isSelected();
                boolean isAssigned = chkAssigned.isSelected();

                AddPlayerAction.addPlayer(playerId, firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, isRegistered, isAssigned);

                // Clear input fields to blank for next addition
                txtPlayerId.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtBirthdate.setText("");
                chkMale.setSelected(false);
                chkFemale.setSelected(false);
                txtSkillLevel.setText("");
                txtSeasonsPlayed.setText("");
                chkRegistered.setSelected(false);
                chkAssigned.setSelected(false);

                // Refresh table with updated data
                model.setRowCount(0);

                try {
                    Connection connection = DatabaseConnection.openConnection();
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerInformation");
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int id = resultSet.getInt("PlayerId");
                        String fName = resultSet.getString("FirstName");
                        String lName = resultSet.getString("LastName");
                        String bdate = resultSet.getString("Birthdate");
                        String g = resultSet.getString("Gender");
                        int sLevel = resultSet.getInt("SkillLevel");
                        int sPlayed = resultSet.getInt("SeasonsPlayed");
                        String registered = resultSet.getString("Registered");
                        String assigned = resultSet.getString("Assigned");

                        model.addRow(new Object[] { id, fName, lName, bdate, g, sLevel, sPlayed, registered, assigned });
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Player added successfully.");
            }
            
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Add your code here to go back to main menu or hide the current window
            }
        });
        
        btnAddPlayer.setBounds(383, 329, 89, 23);
        contentPane.add(btnAddPlayer);
    }
}