package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(catalog = "game_statistics", name = "achievements")
public class AchievementJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "player_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID playerId;

    @Column(name = "game_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID gameId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "total_games_played")
    private Integer totalGamesPlayed;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "total_time_played")
    private Double totalTimePlayed;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description, Boolean isLocked, Integer totalScore, Integer totalGamesPlayed, Integer wins, Double totalTimePlayed) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.totalTimePlayed = totalTimePlayed;
    }

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description, Integer totalScore, Integer totalGamesPlayed, Integer wins, Double totalTimePlayed) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = true;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.totalTimePlayed = totalTimePlayed;
    }

    public Integer getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isLocked() {
        return isLocked;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public Integer getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public Integer getWins() {
        return wins;
    }

    public Double getTotalTimePlayed() {
        return totalTimePlayed;
    }
}
