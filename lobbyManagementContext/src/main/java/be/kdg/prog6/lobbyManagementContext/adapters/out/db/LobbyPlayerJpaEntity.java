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

    // Getters and setters
}