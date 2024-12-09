package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendJpaRepository extends JpaRepository<FriendJpaEntity, UUID> {
    List<FriendJpaEntity> findByPlayerId(UUID playerId);

    @Query("SELECT f FROM FriendJpaEntity f WHERE f.playerId = :playerId AND f.players IN :players")
    FriendJpaEntity findByPlayerIdAndPlayers(@Param("playerId") UUID playerId, @Param("players") List<PlayerJpaEntity> players);
}
