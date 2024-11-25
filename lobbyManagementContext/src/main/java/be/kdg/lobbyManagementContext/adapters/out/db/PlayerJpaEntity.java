package be.kdg.lobbyManagementContext.adapters.out.db;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(catalog = "lobby_management", name = "player")
public class PlayerJpaEntity {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "name")
    private String name;

    @

    // Getters and setters
}