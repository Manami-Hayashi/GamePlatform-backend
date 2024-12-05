package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity, UUID> {
    List<FriendJpaEntity> findByPlayerId(UUID playerId);

    FriendJpaEntity findByPlayerIdAndPlayer(UUID friendId, PlayerJpaEntity player);
}
