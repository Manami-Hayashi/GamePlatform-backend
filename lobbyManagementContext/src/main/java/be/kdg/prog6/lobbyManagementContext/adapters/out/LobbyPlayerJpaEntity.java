package be.kdg.prog6.lobbyManagementContext.adapters.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(catalog = "lobby", name = "player")
public class LobbyPlayerJpaEntity {
    @Id
    @Column(name="player_id")
    private UUID playerId;

    @Column(name="name")
    private String name;

    public LobbyPlayerJpaEntity() {
    }

    public LobbyPlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
