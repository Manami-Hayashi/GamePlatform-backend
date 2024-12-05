// LobbyPlayerJpaEntity.java
package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "player")
public class LobbyPlayerJpaEntity {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_active")
    private Instant lastActive;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private LobbyJpaEntity lobby;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private LobbyGameJpaEntity game;

    @OneToMany(mappedBy = "player")
    private List<LobbyGameJpaEntity> games;

    @Column(name = "ready")
    private Boolean ready = false; // Use Boolean instead of boolean

    // Constructors, getters, and setters
    public LobbyPlayerJpaEntity(UUID playerId, String name, Instant lastActive, LobbyJpaEntity lobby, Boolean ready) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobby = lobby;
        this.ready = ready != null ? ready : false; // Initialize to false if null
    }

    public LobbyPlayerJpaEntity() {
    }

    public LobbyPlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = Instant.now();
        this.ready = false;
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

    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }

    public LobbyJpaEntity getLobby() {
        return lobby;
    }

    public void setLobby(LobbyJpaEntity lobby) {
        this.lobby = lobby;
    }

    public LobbyGameJpaEntity getGame() {
        return game;
    }

    public void setGame(LobbyGameJpaEntity game) {
        this.game = game;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }


}