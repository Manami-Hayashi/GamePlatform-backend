package be.kdg.prog6.gameManagementContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("gameManagementJpaRepository")
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
    Optional<GameJpaEntity> findByGameName(String gameName);

}