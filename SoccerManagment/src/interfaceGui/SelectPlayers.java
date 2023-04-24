package interfaceGui;

import soccerManagment.DatabaseConnection;
import soccerManagment.TeamCreationController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

//Selecting players for team creation
//League determines min and max players allowed on a team 
public class SelectPlayers extends JPanel {

    private Connection connection;
    private JList<String> playerList;
    private DefaultListModel<String> playerListModel;
    private LocalDate lowBDCutOff;
    private LocalDate highBDCutOff;
    private int selectedTeamId;
    private int minPlayers;
    private int maxPlayers;

    public SelectPlayers(LocalDate lowBDCutOff, LocalDate highBDCutOff, int selectedTeamId, String selectedLeague) {
        this.lowBDCutOff = lowBDCutOff;
        this.highBDCutOff = highBDCutOff;
        this.selectedTeamId = selectedTeamId;
        connection = DatabaseConnection.openConnection();
        //applying the appropriate min and max that is needed depend on league
        getMinMaxPlayersForLeague(selectedTeamId, selectedLeague);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        connection = DatabaseConnection.openConnection();
        setLayout(new BorderLayout());

        // Show a list of all players in the league
        JLabel allPlayersLabel = new JLabel("All players in league (CTRL + CLICK FOR MULTI SELECTION):");
        add(allPlayersLabel, BorderLayout.NORTH);

        playerListModel = new DefaultListModel<>();
        JScrollPane playerListScrollPane = new JScrollPane();
        playerListScrollPane.setBounds(10, 50, 258, 130);
        add(playerListScrollPane, BorderLayout.CENTER);
        playerList = new JList<>(playerListModel);
        playerList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        playerListScrollPane.setViewportView(playerList);

        // Add a button to select players and create teams
        JButton createTeamsButton = new JButton("Create Teams");
        add(createTeamsButton, BorderLayout.SOUTH);

        createTeamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedPlayerIndices = playerList.getSelectedIndices();
                int currentTeamPlayers = getCurrentTeamPlayers(selectedTeamId);
                if (selectedPlayerIndices.length + currentTeamPlayers <= maxPlayers) {
                    if (selectedPlayerIndices.length >= minPlayers) {
                        for (int selectedIndex : selectedPlayerIndices) {
                            int playerId = getPlayerIdFromList(selectedIndex);
                            TeamCreationController.assignPlayerToTeam(playerId, selectedTeamId, selectedLeague);
                        }
                        JOptionPane.showMessageDialog(null, "Players have been assigned to the team", "Success", JOptionPane.INFORMATION_MESSAGE);
                        playerListModel.clear();
                        populatePlayersInLeagueListModel(playerListModel);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select at least " + minPlayers + " players", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You cannot add more players. The team is already at the maximum number of players allowed (" + maxPlayers + ")", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        populatePlayersInLeagueListModel(playerListModel);
    }
    //Obtain number of players on teams from PlayerInformation based upon teamID
    private int getCurrentTeamPlayers(int teamId) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as PlayerCount FROM PlayerInformation WHERE TeamId = ?");
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("PlayerCount");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    //Selecting min and max players allowed for leagues 
    private void getMinMaxPlayersForLeague(int teamId, String selectedLeague) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MinPlayers, MaxPlayers FROM SoccerLeague WHERE LeagueName IN (SELECT LeagueName FROM SoccerTeams WHERE TeamId = ? AND LeagueName = ?)");
            statement.setInt(1, teamId);
            statement.setString(2, selectedLeague);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                minPlayers = resultSet.getInt("MinPlayers");
                maxPlayers = resultSet.getInt("MaxPlayers");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Select players in high and low cut off and aren't assigned to team
    private void populatePlayersInLeagueListModel(DefaultListModel<String> model) {
        try {
        	PreparedStatement statement = connection.prepareStatement("SELECT * FROM PlayerInformation WHERE Birthdate >= ? AND Birthdate <= ? AND TeamId = 0");
            statement.setDate(1, java.sql.Date.valueOf(lowBDCutOff));
            statement.setDate(2, java.sql.Date.valueOf(highBDCutOff));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                addPlayerToListModel(model, resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Display players to list model 
    private void addPlayerToListModel(DefaultListModel<String> model, ResultSet resultSet) throws SQLException {
        int playerId = resultSet.getInt("PlayerId");
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        String league = resultSet.getString("League");
        java.sql.Date birthdate = resultSet.getDate("Birthdate");
        int skillLevel = resultSet.getInt("SkillLevel");
        boolean assigned = resultSet.getBoolean("Assigned");
        boolean registered = resultSet.getBoolean("Registered");
        int teamId = resultSet.getInt("TeamId");

        if (teamId == 0) {
            String assignedString = assigned ? "Yes" : "No";
            String registeredString = registered ? "Yes" : "No";

            model.addElement(playerId + ": " + firstName + " " + lastName + " (" + league + ") - Birthdate: " + birthdate + ", Skill Level: " + skillLevel + ", Assigned: " + assignedString + ", Registered: " + registeredString);
        }
    }
    //Select player id from list model
    //Simple split method for distinguishing 
    private int getPlayerIdFromList(int selectedIndex) {
        String selectedPlayer = playerListModel.get(selectedIndex);
        String[] splittedPlayer = selectedPlayer.split(":");
        return Integer.parseInt(splittedPlayer[0]);
    }
}
        