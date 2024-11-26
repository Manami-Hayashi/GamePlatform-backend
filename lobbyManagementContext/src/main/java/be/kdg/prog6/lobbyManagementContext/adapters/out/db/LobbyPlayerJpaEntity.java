package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "player")
public class LobbyPlayerJpaEntity {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private LobbyJpaEntity lobby;

    public LobbyPlayerJpaEntity(UUID playerId, String name, LobbyJpaEntity lobby) {
        this.playerId = playerId;
        this.name = name;
        this.lobby = lobby;
    }

    public LobbyPlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public LobbyPlayerJpaEntity() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LobbyJpaEntity getLobby() {
        return lobby;
    }

    public void setLobby(LobbyJpaEntity lobby) {
        this.lobby = lobby;
    }
    // Getters and setters
}