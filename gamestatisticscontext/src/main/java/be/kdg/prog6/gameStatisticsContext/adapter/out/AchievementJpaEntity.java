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
    private int id;

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
    private boolean isLocked;

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "total_games_played")
    private int totalGamesPlayed;

    @Column(name = "wins")
    private int wins;

    @Column(name = "total_time_played")
    private double totalTimePlayed;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description, boolean isLocked) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = true;
    }

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description, boolean isLocked, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
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

    public AchievementJpaEntity(UUID playerId, UUID gameId, String name, String description, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
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

    public int getId() {
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

    public boolean isLocked() {
        return isLocked;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }
}
