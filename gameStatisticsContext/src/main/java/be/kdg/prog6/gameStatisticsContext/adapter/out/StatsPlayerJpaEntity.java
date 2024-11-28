package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(catalog = "game_statistics", name = "players")
public class StatsPlayerJpaEntity {
    @Id
    @Column(name = "id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GameStatisticsJpaEntity> gameStatistics = new HashSet<>();

    public StatsPlayerJpaEntity() {
    }

    public StatsPlayerJpaEntity(UUID id, String name, int age, String gender, String location, Set<GameStatisticsJpaEntity> gameStatistics) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.gameStatistics = gameStatistics;
    }

    public StatsPlayerJpaEntity(UUID id, String name, int age, String gender, String location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender= gender;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public Set<GameStatisticsJpaEntity> getGameStatistics() {
        return gameStatistics;
    }
}
