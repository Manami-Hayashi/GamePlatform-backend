package be.kdg.PlayerManagementContext.domain;

public class Profile {
    private PlayerId playerId;
    private String bio;
    private String avatar;

    public Profile(PlayerId playerId, String bio, String avatar) {
        this.playerId = playerId;
        this.bio = bio;
        this.avatar = avatar;
    }

    public void updateProfile(String bio, String avatar) {
        this.bio = bio;
        this.avatar = avatar;
    }

}
