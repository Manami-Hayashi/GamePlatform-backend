package be.kdg.prog6.gameManagementContext.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(catalog = "gameManagement", name = "game")
public class GameJpaEntity {
    @Id
    @Column(name="game_id", columnDefinition = "CHAR(36)")
    private UUID gameId;
    @Column(name="game_name")
    private String gameName;
    @Column(name="description")
    private String description;

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}