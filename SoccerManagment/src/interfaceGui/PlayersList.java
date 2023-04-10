package interfaceGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import soccerManagment.DatabaseConnection;
//import soccerManagment.PlayerInformation;

public class PlayersList extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    //private ArrayList<PlayerInformation> playersList;
    private JButton btnNewButton_1;

    /**
     * Launch the application.
     */
    

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

        // Create table
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 664, 239);
        contentPane.add(scrollPane);

        // Set up the table model for the table
        model = new DefaultTableModel(new String[] { "Player ID", "First Name", "Last Name", "Birthdate", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned" }, 0);
        table.setModel(model);

        JButton btnNewButton = new JButton("Add Player");
        btnNewButton.setBounds(85, 282, 121, 53);
        contentPane.add(btnNewButton);

        btnNewButton_1 = new JButton("Search");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and display new JFrame
                PlayerSearch searchPlayerFrame = new PlayerSearch();
                searchPlayerFrame.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(358, 282, 121, 53);
        contentPane.add(btnNewButton_1);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and display new JFrame
                AddPlayerForm addPlayerFrame = new AddPlayerForm();
                addPlayerFrame.setVisible(true);
            }
        });


        // update the table every 2 seconds 
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        timer.start();
    }
        
        public void updateTable() {
            model.setRowCount(0); // Clear the table

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

                    model.addRow(new Object[]{playerId, firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, registered, assigned});
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
}