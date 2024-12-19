package be.kdg.prog6.gameManagementContext.domain;

public class Player {
    private PlayerId playerId;
    private String name;
    private int age;
    private Gender gender;
    private String location;

    public Player(PlayerId playerId, String name, int age, Gender gender, String location) {
        this.playerId = playerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.age = 0; // Default value for age
        this.gender = null; // Default value for gender
        this.location = ""; // Default value for location
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }
}
