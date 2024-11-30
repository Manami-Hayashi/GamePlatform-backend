package be.kdg.prog6.lobbyManagementContext.adapters.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyPlayerJpaRepository extends JpaRepository<LobbyPlayerJpaEntity, String> {
    LobbyPlayerJpaEntity findByName(String name);
}
