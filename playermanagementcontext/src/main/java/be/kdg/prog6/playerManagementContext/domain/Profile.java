package be.kdg.prog6.playerManagementContext.domain;

import java.time.LocalDate;

public class Profile {
    private PlayerId playerId;
    private String bio;
    private String avatar;
    private Gender gender;
    private String location;
    private LocalDate birthDate;

    public Profile(PlayerId playerId, String bio, String avatar, Gender gender, String location, LocalDate birthDate) {
        this.playerId = playerId;
        this.bio = bio;
        this.avatar = avatar;
        this.gender = gender;
        this.location = location;
        this.birthDate = birthDate;
    }

    public void updateProfile(String bio, String avatar, Gender gender, String location, LocalDate birthDate) {
        this.bio = bio;
        this.avatar = avatar;
        this.gender = gender;
        this.location = location;
        this.birthDate = birthDate;
    }

    public PlayerId getPlayerId() {return playerId;}

    public void setPlayerId(PlayerId playerId) {this.playerId = playerId;}

    public String getBio() {return bio;}

    public void setBio(String bio) {this.bio = bio;}

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public Gender getGender() {return gender;}

    public void setGender(Gender gender) {this.gender = gender;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public LocalDate getBirthDate() {return birthDate;}

    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

}
