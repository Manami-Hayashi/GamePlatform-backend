package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(catalog = "game_statistics", name = "game_statistics")
public class GameStatisticsJpaEntity {
    @Id
    @Column(name = "game_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID gameId;

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "matches_played")
    private int matchesPlayed;

    public GameStatisticsJpaEntity() {
    }

    public GameStatisticsJpaEntity(UUID gameId, int totalScore, int matchesPlayed) {
        this.gameId = gameId;
        this.totalScore = totalScore;
        this.matchesPlayed = matchesPlayed;
    }

    public UUID getGameId() {
        return gameId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }
}
