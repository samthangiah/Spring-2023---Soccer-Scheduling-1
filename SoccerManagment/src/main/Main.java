package main;

import soccerManagment.DatabaseConnection;
import interfaceGui.PlayersList;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.openConnection();
        PlayersList playersList = new PlayersList();
        playersList.setVisible(true);
    }
}