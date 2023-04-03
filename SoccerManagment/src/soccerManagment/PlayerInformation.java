package soccerManagment;

public class PlayerInformation {
    private int playerId;
    private String firstName;
    private String lastName;
    private Boolean registered;
    private Boolean assigned;

    public PlayerInformation(int playerId, String firstName, String lastName, Boolean registered, Boolean assigned) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registered = registered;
        this.assigned = assigned;
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

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "PlayerInfo [playerId=" + playerId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", registered=" + registered + ", assigned=" + assigned + "]";
    }
}
