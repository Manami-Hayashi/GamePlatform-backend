package be.kdg.prog6.playerManagementContext.adapters.in;

import java.time.LocalDate;

public class ProfileDto {
    private String bio;
    private String avatar;
    private String gender;
    private String location;
    private LocalDate birthDate;

    public ProfileDto(String bio, String avatar, String gender, String location, LocalDate birthDate) {
        this.bio = bio;
        this.avatar = avatar;
        this.gender = gender;
        this.location = location;
        this.birthDate = birthDate;
    }

    public String getBio() {return bio;}

    public String getAvatar() {return avatar;}

    public String getGender() {return gender;}

    public String getLocation() {return location;}

    public LocalDate getBirthDate() {return birthDate;}
}
