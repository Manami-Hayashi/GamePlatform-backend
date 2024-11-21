package be.kdg.PlayerManagementContext.domain;

import java.time.LocalDate;

public class Profile {
    private PlayerId playerId;
    private String bio;
    private String avatar;
    private String location;
    private LocalDate birthDate;

    public Profile(PlayerId playerId, String bio, String avatar, String location, LocalDate birthDate) {
        this.playerId = playerId;
        this.bio = bio;
        this.avatar = avatar;
        this.location = location;
        this.birthDate = birthDate;
    }

    public void updateProfile(String bio, String avatar, String location, LocalDate birthDate) {
        this.bio = bio;
        this.avatar = avatar;
        this.location = location;
        this.birthDate = birthDate;
    }

}
