package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "lobby")
public class LobbyJpaEntity {
    @Id
    @Column(name = "lobby_id")
    private UUID lobbyId;

    @OneToMany(mappedBy = "lobby")
    private List<LobbyPlayerJpaEntity> players;

    // Getters and setters
}