package interfaceGui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import interfaceGui.PrintTeams;

import soccerManagment.DatabaseConnection;

public class LeagueFormReport extends JPanel {

    private Connection connection;
    private JComboBox<String> leagueComboBox;
    private JList<String> allTeamsList;
    private JButton submitButton;
    private JLabel minPlayersLabel;
    private JLabel maxPlayersLabel;

    public LeagueFormReport() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        connection = DatabaseConnection.openConnection();
        setLayout(null);

        JLabel leagueLabel = new JLabel("Select a league:");
        leagueLabel.setBounds(10, 18, 128, 30);
        add(leagueLabel);

        leagueComboBox = new JComboBox<>();
        leagueComboBox.setBounds(10, 50, 80, 21);
        populateLeagueComboBox();
        add(leagueComboBox);
        
        minPlayersLabel = new JLabel("Min Players:");
        minPlayersLabel.setBounds(10, 80, 80, 21);
        add(minPlayersLabel);

        maxPlayersLabel = new JLabel("Max Players:");
        maxPlayersLabel.setBounds(10, 135, 128, 21);
        add(maxPlayersLabel);

        
        
        JLabel allTeamsLabel = new JLabel("All teams:");
        allTeamsLabel.setBounds(172, 27, 102, 13);
        add(allTeamsLabel);

        DefaultListModel<String> allTeamsListModel = new DefaultListModel<>();
        JScrollPane allTeamsListScrollPane = new JScrollPane();
        allTeamsListScrollPane.setBounds(148, 50, 278, 182);
        add(allTeamsListScrollPane);
        allTeamsList = new JList<>(allTeamsListModel);
        allTeamsListScrollPane.setViewportView(allTeamsList);
        
                allTeamsList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        updateSubmitButtonStatus();
                    }
                });

        populateAllTeamsListModel(allTeamsListModel);
        submitButton = new JButton("Select Players");
        submitButton.setBounds(223, 242, 128, 21);
        submitButton.setEnabled(false);
        add(submitButton);
        
        JButton btnPrintButton = new JButton("Print Button");
        btnPrintButton.setBounds(463, 50, 102, 39);
        add(btnPrintButton);

        leagueComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateSubmitButtonStatus();
                updateMinAndMaxPlayersLabels();
            }
        });
        
        btnPrintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                PrintTeams printTeams = new PrintTeams();
                getParent().add(printTeams, "printTeams");
                cardLayout.show(getParent(), "printTeams");
            }
        });

        //Submit button actions to lead to Select players, used for creating team
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedLeague = leagueComboBox.getSelectedItem();
                if (selectedLeague != null) {
                    String selectedLeagueString = selectedLeague.toString();
                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerLeague WHERE LeagueName = ?");
                        statement.setString(1, selectedLeagueString);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate lowBDCutOff = LocalDate.parse(resultSet.getString("LowBDCutOff"), formatter);
                            LocalDate highBDCutOff = LocalDate.parse(resultSet.getString("HighBDCutOff"), formatter);

                            JOptionPane.showMessageDialog(null, "Low Birthdate Cut Off: " + lowBDCutOff + "\nHigh Birthdate Cut Off: " + highBDCutOff, "Cut Off Information", JOptionPane.INFORMATION_MESSAGE);

                            int selectedTeamId = getSelectedTeamId(allTeamsList.getSelectedValue());

                            CardLayout cardLayout = (CardLayout) getParent().getLayout();
                            SelectPlayers selectPlayers = new SelectPlayers(lowBDCutOff, highBDCutOff, selectedTeamId, selectedLeagueString);
                            getParent().add(selectPlayers, "selectPlayers");
                            cardLayout.show(getParent(), "selectPlayers");
                        }

                        resultSet.close();
                        statement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a league", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    //Min and max identifier determined by selected League in combo box 
    private void updateMinAndMaxPlayersLabels() {
        Object selectedLeague = leagueComboBox.getSelectedItem();
        if (selectedLeague != null) {
            String selectedLeagueString = selectedLeague.toString();
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT MinPlayers, MaxPlayers FROM SoccerLeague WHERE LeagueName = ?");
                statement.setString(1, selectedLeagueString);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int minPlayers = resultSet.getInt("MinPlayers");
                    int maxPlayers = resultSet.getInt("MaxPlayers");
                    minPlayersLabel.setText("Min Players: " + minPlayers);
                    maxPlayersLabel.setText("Max Players: " + maxPlayers);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    private void updateSubmitButtonStatus() {
        boolean isSelected = leagueComboBox.getSelectedIndex() != -1 && allTeamsList.getSelectedIndex() != -1;
        submitButton.setEnabled(isSelected);
    }

    //get availible leagues 
    private void populateLeagueComboBox() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerLeague");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String leagueName = resultSet.getString("LeagueName");
                leagueComboBox.addItem(leagueName);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get availible teams 
    private void populateAllTeamsListModel(DefaultListModel<String> model) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerTeams");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String teamName = resultSet.getString("TeamName");
                model.addElement(teamName);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get desired team from list 
    private int getSelectedTeamId(String selectedTeamName) {
        int teamId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM SoccerTeams WHERE TeamName = ?");
            statement.setString(1, selectedTeamName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                teamId = resultSet.getInt("TeamId");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamId;
    }
}