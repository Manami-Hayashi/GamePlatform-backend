package be.kdg.prog6.playerManagementContext.adapters.in;

import java.time.LocalDate;

public class ProfileDto {
    private String bio;
    private String avatar;
    private String location;
    private LocalDate birthDate;

    public ProfileDto(String bio, String avatar, String location, LocalDate birthDate) {
        this.bio = bio;
        this.avatar = avatar;
        this.location = location;
        this.birthDate = birthDate;
    }

    public String getBio() {return bio;}

    public String getAvatar() {return avatar;}

    public String getLocation() {return location;}

    public LocalDate getBirthDate() {return birthDate;}
}
