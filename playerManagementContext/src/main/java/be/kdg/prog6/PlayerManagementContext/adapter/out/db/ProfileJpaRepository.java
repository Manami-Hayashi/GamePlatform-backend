package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, UUID> {
    Optional<ProfileJpaEntity> findByPlayerId(UUID playerId);
}
