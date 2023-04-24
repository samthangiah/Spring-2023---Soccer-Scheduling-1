package soccerManagment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import interfaceGui.TeamWindow;

//Used to ensure player information is as expected for database table
public class PlayerValidationController {

	public static List<String> validatePlayerData(Set<Integer> teamIds, String firstName, String lastName, Date birthdate, String gender, int skillLevel, int seasonsPlayed, boolean isRegistered, boolean isAssigned, String address, String city, String state, int zipCode, int carPool, String league, String jerseySize, String shortSize, String sockSize, String paid, String medicalInsurance, String medicalConcerns, String adultLastName, String adultFirstName, String adultPhone1, String adultPhone2, String adultEmail, String secondAdultLastName, String secondAdultFirstName, String secondAdultPhone1, String secondAdultPhone2, String secondAdultEmail, int TeamID) {
	    List<String> errors = new ArrayList<>();
        
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.add("Error: First name cannot be empty.");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
        	errors.add("Error: Last name cannot be empty.");
        }

        if (birthdate == null || birthdate.toString().equals("1000-10-10")) {
            birthdate = Date.valueOf("2000-10-10");
            errors.add("Error: Birthdate cannot be empty.");
        }

        if (gender == null || gender.trim().isEmpty()) {
        	errors.add("Error: Gender cannot be empty.");
          
        }

        if (skillLevel < 1 || skillLevel > 10) {
        	errors.add("Error: Skill level must be between 1 and 10.");
        }

        if (seasonsPlayed < 0) {
        	errors.add("Error: Seasons played cannot be negative.");
        }

        if (address == null || address.trim().isEmpty()) {
        	errors.add("Error: Address cannot be empty.");
        }

        if (city == null || city.trim().isEmpty()) {
        	errors.add("Error: City cannot be empty.");
        }

        if (state == null || state.trim().isEmpty()) {
        	errors.add("Error: State cannot be empty.");
        }

        if (zipCode <= 0) {
        	errors.add("Error: Zip code must be positive.");
        }

        if (carPool < 0) {
        	errors.add("Error: Car pool cannot be negative.");
        }

        if (league == null || league.trim().isEmpty()) {
        	errors.add("Error: League cannot be empty.");
        }

        if (jerseySize == null || jerseySize.trim().isEmpty()) {
            System.out.println("Error: Jersey size cannot be empty.");
        }

        if (shortSize == null || shortSize.trim().isEmpty()) {
        	errors.add("Error: Short size cannot be empty.");
        }

        if (sockSize == null || sockSize.trim().isEmpty()) {
        	errors.add("Error: Sock size cannot be empty.");
        }

        if (paid == null || paid.trim().isEmpty()) {
        	errors.add("Error: Paid status cannot be empty.");
        }

        if (medicalInsurance == null || medicalInsurance.trim().isEmpty()) {
        	errors.add("Error: Medical insurance cannot be empty.");
        }
       
        if (adultLastName == null || adultLastName.trim().isEmpty()) {
        	errors.add("Error: Adult last name cannot be empty.");
        }

        if (adultFirstName == null || adultFirstName.trim().isEmpty()) {
        	errors.add("Error: Adult first name cannot be empty.");
        }

        if (adultPhone1 == null || adultPhone1.trim().isEmpty()) {
        	errors.add("Error: Adult phone 1 cannot be empty.");
        }

        if (adultPhone2 == null|| adultPhone2.trim().isEmpty()) {
        	errors.add("Error: Adult phone 2 cannot be null.");
        }

        if (adultEmail == null || adultEmail.trim().isEmpty()) {
        	errors.add("Error: Adult email cannot be empty.");
        }
        if (secondAdultLastName == null || secondAdultLastName.trim().isEmpty()) {
        	errors.add("Error: Second Adult last name cannot be empty.");
        }

        if (secondAdultFirstName == null || secondAdultFirstName.trim().isEmpty()) {
        	errors.add("Error: Second Adult first name cannot be empty.");
        }

        if (secondAdultPhone1 == null || secondAdultPhone1.trim().isEmpty()) {
        	errors.add("Error: Second Adult phone 1 cannot be empty.");
        }

        if (secondAdultPhone2 == null || secondAdultPhone2.trim().isEmpty()) {
        	errors.add("Error: Second Adult phone 2 cannot be null.");
        }

        if (secondAdultEmail == null || secondAdultEmail.trim().isEmpty()) {
        	errors.add("Error: Second Adult email cannot be empty.");
        }
        
        if (TeamID < 0) {
            errors.add("Error: Team cannot be negative.");
        }

        if (TeamID != 0 && teamIds != null && !teamIds.contains(TeamID)) {
            errors.add("Error: Team ID does not exist in the roster.");
        }
		return errors;

    }

}
        