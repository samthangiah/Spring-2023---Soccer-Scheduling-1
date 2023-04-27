package interfaceGui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.print.PrinterException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import soccerManagment.DatabaseConnection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable.PrintMode;

//Used for printing of created teams from LeagueFormReport
public class PrintTeams extends JPanel {

    /**
     * Create the panel.
     */
    public PrintTeams() {
        setLayout(null);

        // Create a table to display the player information
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 709, 332);
        add(scrollPane);

        //Initialize default table model for printing 
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Info", "Team Name", "First Name", "Last Name", "Gender", "Skill Level", "Seasons Played", "Registered", "Assigned", "League"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        //resize of width for rows 
        table.setModel(model);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(250); // Info
        columnModel.getColumn(1).setPreferredWidth(150); // Team Name
        columnModel.getColumn(2).setPreferredWidth(150); // First Name
        columnModel.getColumn(3).setPreferredWidth(150); // Last Name
        columnModel.getColumn(4).setPreferredWidth(80); // Gender
        columnModel.getColumn(5).setPreferredWidth(110); // Skill Level
        columnModel.getColumn(6).setPreferredWidth(120); // Seasons Played
        columnModel.getColumn(7).setPreferredWidth(80); // Registered
        columnModel.getColumn(8).setPreferredWidth(80); // Assigned
        columnModel.getColumn(9).setPreferredWidth(150); // League

        // Retrieve the player information from the database and add it to the table
        Connection connection = DatabaseConnection.openConnection();
        //Implementation of mapping for handling two different tables team ad player
        Map<String, Integer> teamSkillLevels = new HashMap<>();
        try {
        	//Statement includes values from different tables
        	//t.table p.players
        	PreparedStatement statement = connection.prepareStatement("SELECT t.TeamName, p.FirstName, p.LastName, p.Gender, p.SkillLevel, p.SeasonsPlayed, p.Registered, p.Assigned, p.League FROM PlayerInformation p INNER JOIN SoccerTeams t ON p.TeamId = t.TeamID WHERE p.TeamId > 0 ORDER BY p.League");
            ResultSet resultSet = statement.executeQuery();

            String currentLeague = null;
            //Retrieve desired column names
            while (resultSet.next()) {
                String league = resultSet.getString("League");
                String teamName = resultSet.getString("TeamName");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String gender = resultSet.getString("Gender");
                int skillLevel = resultSet.getInt("SkillLevel");
                int seasonsPlayed = resultSet.getInt("SeasonsPlayed");
                boolean registered = resultSet.getBoolean("Registered");
                boolean assigned = resultSet.getBoolean("Assigned");
                teamSkillLevels.put(teamName, teamSkillLevels.getOrDefault(teamName, 0) + skillLevel);

                //Model of list for printing 
                if (currentLeague == null || !currentLeague.equals(league)) {
                    currentLeague = league;
                    model.addRow(new Object[]{"League: " + league, "", "", "", "", "", "", "", ""});
                }

                //Add row to list for printing layout 
                model.addRow(new Object[]{"", teamName, firstName, lastName, gender, skillLevel, seasonsPlayed, registered, assigned, league});
            }

            resultSet.close();
            statement.close();
            
            //Blank row to distinguish between league list and total skill per team 
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", ""});

            for (Map.Entry<String, Integer> entry : teamSkillLevels.entrySet()) {
                String teamName = entry.getKey();
                int totalSkillLevel = entry.getValue();
                model.addRow(new Object[]{"Total Skill of " + teamName, "", "", "", totalSkillLevel, "", "", "", ""});
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(362, 342, 250, 21);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                // "leagueFormReport" is the name used when adding the LeagueFormReport panel
                cardLayout.show(getParent(), "leagueFormReport"); 
            }
        });
        add(backButton);
    
        //Print button  along with success message implementtion
        JButton previewButton = new JButton("Print");
        previewButton.setBounds(36, 342, 250, 21);
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean complete = table.print(PrintMode.FIT_WIDTH);
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "Printing Complete", "Print Preview", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Printing Cancelled", "Print Preview", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(null, "Printing Failed", "Print Preview", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); 
        add(previewButton);
    } 
}