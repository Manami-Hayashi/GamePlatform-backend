package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface AchievementRepository extends JpaRepository<AchievementJpaEntity, Integer> {
    List<AchievementJpaEntity> findByPlayerIdAndGameId(UUID playerId, UUID gameId);
}
