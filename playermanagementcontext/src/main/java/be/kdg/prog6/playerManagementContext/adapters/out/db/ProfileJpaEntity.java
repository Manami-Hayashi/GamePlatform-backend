package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(catalog="player_management", name="profiles")
public class ProfileJpaEntity {

    @Id
    UUID playerId;

    @Column(name="bio")
    private String bio;
    @Column(name="avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;
    @Column(name="location")
    private String location;
    @Column(name="birth_date")
    private LocalDate birthDate;

    public ProfileJpaEntity() {
    }

    public ProfileJpaEntity(UUID playerId, String bio, String avatar, Gender gender, String location, LocalDate birthDate) {
        this.playerId = playerId;
        this.bio = bio;
        this.avatar = avatar;
        this.gender = gender;
        this.location = location;
        this.birthDate = birthDate;
    }

    public UUID getPlayerId() {return playerId;}

    public void setPlayerId(UUID playerId) {this.playerId = playerId;}

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
