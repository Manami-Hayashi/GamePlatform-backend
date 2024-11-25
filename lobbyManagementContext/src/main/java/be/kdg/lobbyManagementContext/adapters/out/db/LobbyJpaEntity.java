package be.kdg.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "lobby")
public class LobbyJpaEntity {
    @Id
    @Column(name = "lobby_id")
    private UUID lobbyId;

    @OneToMany
    @JoinColumn(name = "lobby_id")
    private List<PlayerJpaEntity> players;

    // Getters and setters
}