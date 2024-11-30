package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(catalog = "game_statistics", name = "match_session")
public class MatchSessionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID gameId;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "match_session_game_statistics",
            joinColumns = @JoinColumn(name = "match_session_id"),
            inverseJoinColumns = @JoinColumn(name = "game_statistics_id")
    )
    private Set<GameStatisticsJpaEntity> gameStatistics;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "winner")
    private String winner;

    @Column(name = "score")
    private int score;

    @Column(name = "moves_made")
    private int movesMade;

    public MatchSessionJpaEntity() {
    }

    public MatchSessionJpaEntity(UUID gameId, Set<GameStatisticsJpaEntity> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, String winner, int score, int movesMade) {
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.score = score;
        this.movesMade = movesMade;
    }

    public MatchSessionJpaEntity(int id, UUID gameId, Set<GameStatisticsJpaEntity> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, String winner, int score, int movesMade) {
        this.id = id;
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.score = score;
        this.movesMade = movesMade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Set<GameStatisticsJpaEntity> getGameStatistics() {
        return gameStatistics;
    }

    public void setGameStatistics(Set<GameStatisticsJpaEntity> gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }
}
