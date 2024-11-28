package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "game")
public class LobbyGameJpaEntity {
    @Id
    @Column(name = "game_id")
    private UUID gameId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "game")
    private List<LobbyJpaEntity> lobbies;



    public LobbyGameJpaEntity(UUID gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public LobbyGameJpaEntity() {
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LobbyJpaEntity> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<LobbyJpaEntity> lobbies) {
        this.lobbies = lobbies;
    }


}