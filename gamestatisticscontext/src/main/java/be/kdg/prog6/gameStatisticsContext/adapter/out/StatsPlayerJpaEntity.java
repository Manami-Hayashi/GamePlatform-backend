package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
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

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "location")
    private String location;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id")
    private List<GameStatisticsJpaEntity> gameStatistics;

    public StatsPlayerJpaEntity() {
    }

    public StatsPlayerJpaEntity(UUID id, String name, String birthDate, String gender, String location) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
    }

    public StatsPlayerJpaEntity(UUID id, String name, String birthDate, String gender, String location, List<GameStatisticsJpaEntity> gameStatistics) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
        this.gameStatistics = gameStatistics;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public List<GameStatisticsJpaEntity> getGameStatistics() {
        return gameStatistics;
    }
}
