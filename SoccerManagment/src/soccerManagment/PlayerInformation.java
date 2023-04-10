/*
package soccerManagment;

import java.sql.Date;

public class PlayerInformation {
    private int playerId;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String gender;
    private int skillLevel;
    private int seasonsPlayed;
    private boolean registered;
    private boolean assigned;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String carPool;
    private String league;
    private String jerseySize;
    private String shortSize;
    private String sockSize;
    private String medicalInsurer;
    private String medicalConcerns;
    private String adultLastName;
    private String adultFirstName;
    private String adultPhone1;
    private String adultPhone2;
    private String adultEmail;

    public PlayerInformation(int playerId, String firstName, String lastName, Date birthdate, String gender, int skillLevel, int seasonsPlayed, boolean registered, boolean assigned, String address, String city, String state, String zipCode, String carPool, String league, String jerseySize, String shortSize, String sockSize, String medicalInsurer, String medicalConcerns, String adultLastName, String adultFirstName, String adultPhone1, String adultPhone2, String adultEmail) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.skillLevel = skillLevel;
        this.seasonsPlayed = seasonsPlayed;
        this.registered = registered;
        this.assigned = assigned;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.carPool = carPool;
        this.league = league;
        this.jerseySize = jerseySize;
        this.shortSize = shortSize;
        this.sockSize = sockSize;
        this.medicalInsurer = medicalInsurer;
        this.medicalConcerns = medicalConcerns;
        this.adultLastName = adultLastName;
        this.adultFirstName = adultFirstName;
        this.adultPhone1 = adultPhone1;
        this.adultPhone2 = adultPhone2;
        this.adultEmail = adultEmail;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getSeasonsPlayed() {
        return seasonsPlayed;
    }

    public void setSeasonsPlayed(int seasonsPlayed) {
        this.seasonsPlayed = seasonsPlayed;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
    
    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCarPool() {
        return carPool;
    }

    public void setCarPool(String carPool) {
        this.carPool = carPool;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getJerseySize() {
        return jerseySize;
    }

    public void setJerseySize(String jerseySize) {
        this.jerseySize = jerseySize;
    }

    public String getShortSize() {
        return shortSize;
    }

    public void setShortSize(String shortSize) {
        this.shortSize = shortSize;
    }

    public String getSockSize() {
        return sockSize;
    }

    public void setSockSize(String sockSize) {
        this.sockSize = sockSize;
    }

    public String getMedicalInsurer() {
        return medicalInsurer;
    }

    public void setMedicalInsurer(String medicalInsurer) {
        this.medicalInsurer = medicalInsurer;
    }

    public String getMedicalConcerns() {
        return medicalConcerns;
    }

    public void setMedicalConcerns(String medicalConcerns) {
        this.medicalConcerns = medicalConcerns;
    }

    public String getAdultLastName() {
        return adultLastName;
    }

    public void setAdultLastName(String adultLastName) {
        this.adultLastName = adultLastName;
    }

    public String getAdultFirstName() {
        return adultFirstName;
    }

    public void setAdultFirstName(String adultFirstName) {
        this.adultFirstName = adultFirstName;
    }

    public String getAdultPhone1() {
        return adultPhone1;
    }

    public void setAdultPhone1(String adultPhone1) {
        this.adultPhone1 = adultPhone1;
    }

    public String getAdultPhone2() {
        return adultPhone2;
    }

    public void setAdultPhone2(String adultPhone2) {
        this.adultPhone2 = adultPhone2;
    }

    public String getAdultEmail() {
        return adultEmail;
    }

    public void setAdultEmail(String adultEmail) {
        this.adultEmail = adultEmail;
    }

    @Override
    public String toString() {
        return "PlayerInformation{" +
        		"playerId=" + playerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", gender='" + gender + '\'' +
                ", skillLevel=" + skillLevel +
                ", seasonsPlayed=" + seasonsPlayed +
                ", registered=" + registered +
                ", assigned=" + assigned +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", carPool='" + carPool + '\'' +
                ", league='" + league + '\'' +
                ", jerseySize='" + jerseySize + '\'' +
                ", shortSize='" + shortSize + '\'' +
                ", sockSize='" + sockSize + '\'' +
                ", medicalInsurer='" + medicalInsurer + '\'' +
                ", medicalConcerns='" + medicalConcerns + '\'' +
                ", adultLastName='" + adultLastName + '\'' +
                ", adultFirstName='" + adultFirstName + '\'' +
                ", adultPhone1='" + adultPhone1 + '\'' +
                ", adultPhone2='" + adultPhone2 + '\'' +
                ", adultEmail='" + adultEmail + '\'' +
                '}';
    }
}
*/