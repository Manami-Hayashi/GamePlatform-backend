package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
    PlayerJpaEntity findByName(String name);

    @Query("SELECT p FROM PlayerJpaEntity p JOIN FETCH p.gameOwned WHERE p.playerId = :playerId")
    Optional<PlayerJpaEntity> findByIdWithGameOwned(@Param("playerId")PlayerId playerId);
}
