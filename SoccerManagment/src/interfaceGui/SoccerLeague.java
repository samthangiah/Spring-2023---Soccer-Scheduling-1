package interfaceGui;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import soccerManagment.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Timer;

public class SoccerLeague extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    public SoccerLeague() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 503);
        setTitle("Soccer League Information");

        // Create contentPane with BorderLayout
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Create left panel with tabs
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        contentPane.add(leftPanel, BorderLayout.WEST);

        JButton btnSoccerLeagues = new JButton("Soccer Leagues");
        leftPanel.add(btnSoccerLeagues);

        JButton btnAddL = new JButton("Add League");
        leftPanel.add(btnAddL);

        JButton btnNewView = new JButton("Search");
        leftPanel.add(btnNewView);

        // Create right panel with card layout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new CardLayout(0, 0));
        contentPane.add(rightPanel, BorderLayout.CENTER);

        // Create table panel and add it to the right panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        table = new JTable();
        scrollPane = new JScrollPane(table);
        model = new DefaultTableModel(new String[]{"LeagueName", "HighBDCutOff", "LowBDCutOff", "NumCoaches", "MinPlayers", "MaxPlayers", "TeamList"}, 0);
        table.setModel(model);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(tablePanel, "soccerLeagues");

        // Create other panel and add it to the right panel
        JPanel addLeaguePanel = new JPanel();
        addLeaguePanel.setLayout(new BorderLayout());
        JLabel addLeagueLabel = new JLabel("This is the other panel");
        addLeaguePanel.add(addLeagueLabel, BorderLayout.CENTER);
        rightPanel.add(addLeaguePanel, "other");

        // Set up tab button actions
        btnSoccerLeagues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "soccerLeagues");
            }
        });
        //Add  League action
        btnAddL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddLeagueForm addLeagueForm = new AddLeagueForm();
                rightPanel.add(addLeagueForm, "addLeague");
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "addLeague");
            }
        });
        //League search action
        btnNewView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LeagueSearch leagueSearchPanel = new LeagueSearch(rightPanel);
                rightPanel.add(leagueSearchPanel, "leagueSearch");
                CardLayout cardLayout = (CardLayout) rightPanel.getLayout();
                cardLayout.show(rightPanel, "leagueSearch");
            }
        });
        //Auto update to update the table 
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        timer.start();
    }
    public void updateTable() {
        // Clear the current contents of the table model
        model.setRowCount(0); 

        try {
        	//Display of all leagues present
            Connection connection = DatabaseConnection.openConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerLeague");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String leagueName = resultSet.getString("LeagueName");
                Date lowDBCutOff = resultSet.getDate("LowBDCutOff");
                Date highDBCutOff = resultSet.getDate("HighBDCutOff");
                int numCoaches = resultSet.getInt("NumCoaches");
                int minPlayers = resultSet.getInt("MinPlayers");
                int maxPlayers = resultSet.getInt("MaxPlayers");
                // Retrieve the TeamList column from the ResultSet
                String teamList = resultSet.getString("TeamList"); 

                // Format the dates using a SimpleDateFormat object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Add a new row to the table model with the retrieved data
                model.addRow(new Object[]{leagueName, dateFormat.format(highDBCutOff), dateFormat.format(lowDBCutOff), numCoaches, minPlayers, maxPlayers, teamList});
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