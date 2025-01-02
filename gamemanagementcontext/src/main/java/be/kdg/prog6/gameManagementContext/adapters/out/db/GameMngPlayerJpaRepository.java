package be.kdg.prog6.gameManagementContext.adapters.out.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameMngPlayerJpaRepository extends JpaRepository<GameMngPlayerJpaEntity, UUID> {
}
