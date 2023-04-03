package main;

import soccerManagment.DatabaseConnection;
import interfaceGui.MainMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.openConnection();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}