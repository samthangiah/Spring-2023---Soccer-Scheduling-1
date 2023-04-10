package soccerManagment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:ucanaccess://databaseFolder/soccerDB4.mdb";

    private DatabaseConnection() {}

    public static Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
           // System.out.println("Connection established.");
        } catch (Exception e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}