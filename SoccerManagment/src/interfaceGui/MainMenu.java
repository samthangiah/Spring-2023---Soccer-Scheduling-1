package interfaceGui;

//main menu interface for other buttons to be selected from 
//not used due to interface chagne
//used for test
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import interfaceGui.SoccerLeague;
import interfaceGui.LeagueFormReport;

public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenu frame = new MainMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 820, 519);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 128, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Soccer Program Main Menu");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(237, 98, 283, 48);
        contentPane.add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBounds(227, 98, 311, 48);
        contentPane.add(panel);

        JButton btnResetDatabase = new JButton("Reset Database");
        btnResetDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResetDatabase.resetPlayerStatus();
            }
        });
        btnResetDatabase.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnResetDatabase.setBounds(226, 178, 312, 48);
        contentPane.add(btnResetDatabase);

        JButton btnPlayerButton = new JButton("Player Button");
        btnPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayersList playerAddition = new PlayersList();
                playerAddition.setVisible(true);
            }
        });
        btnPlayerButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPlayerButton.setBounds(226, 256, 312, 48);
        contentPane.add(btnPlayerButton);

        JButton btnTeamButton = new JButton("Team Button");
        btnTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TeamWindow teamWindow = new TeamWindow();
                teamWindow.setVisible(true);
            }
        });
        btnTeamButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnTeamButton.setBounds(227, 328, 312, 48);
        contentPane.add(btnTeamButton);

        JButton btnLeagueList = new JButton("League List");
        btnLeagueList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoccerLeague leagueList = new SoccerLeague();
                leagueList.setVisible(true);
            }
        });
        btnLeagueList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnLeagueList.setBounds(227, 400, 312, 48);
        contentPane.add(btnLeagueList);

        JButton btnSetUpLeague = new JButton("Set Up League");
        btnSetUpLeague.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LeagueFormReport setUpLeagueForm = new LeagueFormReport();
                JFrame setUpLeagueFrame = new JFrame("Set Up League");
                setUpLeagueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setUpLeagueFrame.getContentPane().add(setUpLeagueForm);
                setUpLeagueFrame.pack();
                setUpLeagueFrame.setLocationRelativeTo(null);
                setUpLeagueFrame.setVisible(true);
            }
        });
        btnSetUpLeague.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSetUpLeague.setBounds(227, 472, 312, 48);
        contentPane.add(btnSetUpLeague);
    }
}