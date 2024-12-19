package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
    Optional<PlayerJpaEntity> findByPlayerId(UUID playerId);

    @Query("SELECT p FROM PlayerJpaEntity p JOIN FETCH p.gamesOwned WHERE p.playerId = :playerId")
    Optional<PlayerJpaEntity> findByIdWithGameOwned(@Param("playerId") PlayerId playerId);

}
