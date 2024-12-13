package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "lobby")
public class LobbyJpaEntity {
    @Id
    @Column(name = "lobby_id")
    private UUID lobbyId;

    @Column(name = "creation_time")
    private Instant creationTime; // Add this field

    @OneToMany(mappedBy = "lobby")
    private List<LobbyPlayerJpaEntity> players;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private LobbyGameJpaEntity game;

    public LobbyJpaEntity(UUID lobbyId, Instant creationTime, List<LobbyPlayerJpaEntity> players, LobbyGameJpaEntity game) {
        this.lobbyId = lobbyId;
        this.players = players;
        this.game = game;
    }

    public LobbyJpaEntity() {
        this.creationTime = Instant.now(); // Add this line
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(UUID lobbyId) {
        this.lobbyId = lobbyId;
    }

    public List<LobbyPlayerJpaEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<LobbyPlayerJpaEntity> players) {
        this.players = players;
    }

    public LobbyGameJpaEntity getGame() {
        return game;
    }

    public void setGame(LobbyGameJpaEntity game) {
        this.game = game;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
}