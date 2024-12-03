package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "game_statistics", name = "game_statistics")
public class GameStatisticsJpaEntity {
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

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "total_games_played")
    private int totalGamesPlayed;

    @Column(name = "wins")
    private int wins;

    @Column(name = "losses")
    private int losses;

    @Column(name = "draws")
    private int draws;

    @Column(name = "win_loss_ratio")
    private double winLossRatio;

    @Column(name = "total_time_played")
    private double totalTimePlayed;

    @Column(name = "highest_score")
    private int highestScore;

    @Column(name = "moves_made")
    private int movesMade;

    @Column(name = "average_game_duration")
    private double averageGameDuration;

    @ManyToMany(mappedBy = "gameStatistics", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MatchSessionJpaEntity> matchSessions;

    public GameStatisticsJpaEntity() {
    }

    public GameStatisticsJpaEntity(UUID playerId, UUID gameId, int totalScore, int totalGamesPlayed, int wins, int losses, int draws, double winLossRatio, double totalTimePlayed, int highestScore, int movesMade, double averageGameDuration) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.winLossRatio = winLossRatio;
        this.totalTimePlayed = totalTimePlayed;
        this.highestScore = highestScore;
        this.movesMade = movesMade;
        this.averageGameDuration = averageGameDuration;
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

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void setTotalTimePlayed(double totalTimePlayed) {
        this.totalTimePlayed = totalTimePlayed;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }

    public double getAverageGameDuration() {
        return averageGameDuration;
    }

    public void setAverageGameDuration(double averageGameDuration) {
        this.averageGameDuration = averageGameDuration;
    }
}
