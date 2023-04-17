package interfaceGui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import soccerManagment.DatabaseConnection;
import interfaceGui.AddPlayerForm;
import interfaceGui.PlayerSearch;
import interfaceGui.ResetDatabase;


public class PlayersList extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    public PlayersList() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 503);
        setTitle("Player Information");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Add a JToolBar at the top of the contentPane
        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);
        contentPane.add(topToolBar, BorderLayout.NORTH);

        // Add the "Team Window" button to the topToolBar
        JButton btnTeamWindow = new JButton("Team Window");
        topToolBar.add(btnTeamWindow);

        // Create left panel with tabs
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        contentPane.add(leftPanel, BorderLayout.WEST);

        JButton btnPlayerList = new JButton("Player List");
        leftPanel.add(btnPlayerList);

        JButton btnAddPlayer = new JButton("Add Player");
        leftPanel.add(btnAddPlayer);

        JButton btnSearch = new JButton("Search");
        leftPanel.add(btnSearch);

        // Create right panel with tab contents
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new CardLayout(0, 0));
        contentPane.add(rightPanel, BorderLayout.CENTER);

        // Create table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        table = new JTable();
        scrollPane = new JScrollPane(table);
        model = new DefaultTableModel(new String[]{"Player ID", "First Name", "Last Name", "Birthdate", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned", "Team ID"}, 0);
        table.setModel(model);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(tablePanel, "playerList");

        // Add PlayerForm panel
        AddPlayerForm addPlayerForm = new AddPlayerForm();
        rightPanel.add(addPlayerForm, "addPlayerForm");

        // Add PlayerSearch panel
        PlayerSearch playerSearch = new PlayerSearch();
        rightPanel.add(playerSearch, "playerSearch");

        // Add TeamWindow instance to the rightPanel
        //Remove to edit
        TeamWindow teamWindow = new TeamWindow();
        rightPanel.add(teamWindow.getContentPane(), "teamWindow");
        
        JButton btnResetDatabase = new JButton("Reset Database");
        topToolBar.add(btnResetDatabase);

        // Set up tab button actions
        btnPlayerList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "playerList");
            }
        });
        btnAddPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "addPlayerForm");
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "playerSearch");
            }
        });

        btnTeamWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "teamWindow");
            }
        });
        
        btnResetDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResetDatabase.resetPlayerStatus();
            }
        });

        // Update the table every 2 seconds (2000 milliseconds)
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
                Date birthdate = resultSet.getDate("Birthdate");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedBirthdate = dateFormat.format(birthdate);
                String gender = resultSet.getString("Gender");
                int skillLevel = resultSet.getInt("SkillLevel");
                int seasonsPlayed = resultSet.getInt("SeasonsPlayed");
                boolean isRegistered = resultSet.getBoolean("Registered");
                boolean isAssigned = resultSet.getBoolean("Assigned");
                int teamID = resultSet.getInt("TeamID");

                model.addRow(new Object[]{playerId, firstName, lastName, formattedBirthdate, gender, skillLevel, seasonsPlayed, isRegistered, isAssigned, teamID});
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