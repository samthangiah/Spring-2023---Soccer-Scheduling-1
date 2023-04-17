package soccerManagment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddPlayerController {
    
    public static void addPlayer(String firstName, String lastName, Date birthdate, String gender, int skillLevel, int seasonsPlayed, boolean isRegistered, boolean isAssigned, String address, String city, String state, int zipCode, int carPool, String league, String jerseySize, String shortSize, String sockSize, String paid, String medicalInsurance, String medicalConcerns, String adultLastName, String adultFirstName, String adultPhone1, String adultPhone2, String adultEmail, String secondAdultLastName,String secondAdultFirstName,String secondAdultPhone1, String secondAdultPhone2, String secondAdultEmail, int teamID) {
        int playerId = getNextPlayerId();
        
        try {
            Connection con = DatabaseConnection.openConnection();
            String query = "INSERT INTO PlayerInformation (playerId, firstName, lastName, birthdate, gender, skillLevel, seasonsPlayed, Registered, Assigned, Address, City, State, ZipCode, CarPool, League, JerseySize, ShortSize, SockSize, Paid, MedicalInsurance, MedicalConcerns, AdultLastName, AdultFirstName, AdultPhone1, AdultPhone2, AdultEmail, SecondAdultLastName,SecondAdultFirstName,SecondAdultPhone1, SecondAdultPhone2, SecondAdultEmail, TeamID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            
            pstmt.setInt(1, playerId);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setDate(4, birthdate);
            pstmt.setString(5, gender);
            pstmt.setInt(6, skillLevel);
            pstmt.setInt(7, seasonsPlayed);
            pstmt.setBoolean(8, isRegistered);
            pstmt.setBoolean(9, isAssigned);
            pstmt.setString(10, address);
            pstmt.setString(11, city);
            pstmt.setString(12, state);
            pstmt.setInt(13, zipCode);
            pstmt.setInt(14, carPool);
            pstmt.setString(15, league);
            pstmt.setString(16, jerseySize);
            pstmt.setString(17, jerseySize);
            pstmt.setString(18, jerseySize);
            pstmt.setString(19, paid);
            pstmt.setString(20, medicalInsurance);
            pstmt.setString(21, medicalConcerns);
            pstmt.setString(22, adultLastName);
            pstmt.setString(23, adultFirstName);
            pstmt.setString(24, adultPhone1);
            pstmt.setString(25, adultPhone2);
            pstmt.setString(26, adultEmail);
            pstmt.setString(27, secondAdultLastName);
            pstmt.setString(28, secondAdultFirstName);
            pstmt.setString(29, secondAdultPhone1);
            pstmt.setString(30, secondAdultPhone2);
            pstmt.setString(31, secondAdultEmail);
            pstmt.setInt(32, teamID);

            

            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getNextPlayerId() {
        int maxPlayerId = 0;

        try {
            Connection con = DatabaseConnection.openConnection();
            Statement stmt = con.createStatement();
            String query = "SELECT MAX(playerId) as maxPlayerId FROM PlayerInformation";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                maxPlayerId = rs.getInt("maxPlayerId");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxPlayerId + 1;
    }

}