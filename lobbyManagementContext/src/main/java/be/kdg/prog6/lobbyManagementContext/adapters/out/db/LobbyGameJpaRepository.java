package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LobbyGameJpaRepository extends JpaRepository<LobbyGameJpaEntity, UUID> {

    Optional<LobbyGameJpaEntity> findByName(String name);
}