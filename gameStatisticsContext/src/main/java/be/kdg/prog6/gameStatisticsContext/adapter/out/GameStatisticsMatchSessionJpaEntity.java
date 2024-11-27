package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;

@Entity
@Table(catalog = "game_statistics", name = "game_statistics_match_history")
public class GameStatisticsMatchSessionJpaEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "game_statistics_id", referencedColumnName = "game_id")
    private GameStatisticsJpaEntity gameStatistics;

    @Id
    @ManyToOne
    @JoinColumn(name = "match_history_id", referencedColumnName = "id")
    private MatchSessionJpaEntity matchHistory;

    public GameStatisticsMatchSessionJpaEntity() {}

    public GameStatisticsMatchSessionJpaEntity(GameStatisticsJpaEntity gameStatistics, MatchSessionJpaEntity matchHistory) {
        this.gameStatistics = gameStatistics;
        this.matchHistory = matchHistory;
    }

    public GameStatisticsJpaEntity getGameStatistics() {
        return gameStatistics;
    }

    public MatchSessionJpaEntity getMatchHistory() {
        return matchHistory;
    }
}
