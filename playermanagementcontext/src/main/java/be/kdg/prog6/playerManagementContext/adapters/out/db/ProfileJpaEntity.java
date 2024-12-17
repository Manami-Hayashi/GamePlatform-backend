package be.kdg.prog6.playerManagementContext.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    @Column(name="location")
    private String location;
    @Column(name="birth_date")
    private LocalDate birthDate;

    public ProfileJpaEntity() {
    }

    public ProfileJpaEntity(UUID playerId, String bio, String avatar, String location, LocalDate birthDate) {
        this.playerId = playerId;
        this.bio = bio;
        this.avatar = avatar;
        this.location = location;
        this.birthDate = birthDate;
    }

    public UUID getPlayerId() {return playerId;}

    public void setPlayerId(UUID playerId) {this.playerId = playerId;}

    public String getBio() {return bio;}

    public void setBio(String bio) {this.bio = bio;}

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public LocalDate getBirthDate() {return birthDate;}

    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

}
