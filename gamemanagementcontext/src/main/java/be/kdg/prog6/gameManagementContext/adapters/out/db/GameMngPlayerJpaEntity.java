package be.kdg.prog6.gameManagementContext.adapters.out.db;

import be.kdg.prog6.gameManagementContext.domain.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(catalog = "game_management", name = "player")
public class GameMngPlayerJpaEntity {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "location")
    private String location;

    public GameMngPlayerJpaEntity() {
    }

    public GameMngPlayerJpaEntity(UUID id, String name, int age, Gender gender, String location) {
        this.playerId = id;
        this.name = name;
        this.age = age;
        this.gender=gender;
        this.location=location;
    }

    public UUID getPlayerId() {
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
