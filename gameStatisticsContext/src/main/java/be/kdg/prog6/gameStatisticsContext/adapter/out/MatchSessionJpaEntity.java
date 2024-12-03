package be.kdg.prog6.gameStatisticsContext.adapter.out;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
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
    private List<GameStatisticsJpaEntity> gameStatistics;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "winner")
    private String winner;

    @Column(name = "score_p1")
    private int scoreP1;

    @Column(name = "score_p2")
    private int scoreP2;

    @Column(name = "moves_made_P1")
    private int movesMadeP1;

    @Column(name = "moves_made_P2")
    private int movesMadeP2;

    public MatchSessionJpaEntity() {
    }

    public MatchSessionJpaEntity(UUID gameId, List<GameStatisticsJpaEntity> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, String winner, int scoreP1, int scoreP2, int movesMadeP1, int movesMadeP2) {
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.movesMadeP1 = movesMadeP1;
        this.movesMadeP2 = movesMadeP2;
    }

    public MatchSessionJpaEntity(int id, UUID gameId, List<GameStatisticsJpaEntity> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, String winner, int scoreP1, int scoreP2, int movesMadeP1) {
        this.id = id;
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.movesMadeP1 = movesMadeP1;
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

    public List<GameStatisticsJpaEntity> getGameStatistics() {
        return gameStatistics;
    }

    public void setGameStatistics(List<GameStatisticsJpaEntity> gameStatistics) {
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

    public int getScoreP1() {
        return scoreP1;
    }

    public void setScoreP1(int scoreP1) {
        this.scoreP1 = scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }

    public void setScoreP2(int scoreP2) {
        this.scoreP2 = scoreP2;
    }

    public int getMovesMadeP1() {
        return movesMadeP1;
    }

    public void setMovesMadeP1(int movesMade) {
        this.movesMadeP1 = movesMade;
    }

    public int getMovesMadeP2() {
        return movesMadeP2;
    }

    public void setMovesMadeP2(int movesMade) {
        this.movesMadeP2 = movesMade;
    }
}
