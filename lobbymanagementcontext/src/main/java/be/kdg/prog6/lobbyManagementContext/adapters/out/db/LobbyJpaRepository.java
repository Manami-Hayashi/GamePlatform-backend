package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {
}