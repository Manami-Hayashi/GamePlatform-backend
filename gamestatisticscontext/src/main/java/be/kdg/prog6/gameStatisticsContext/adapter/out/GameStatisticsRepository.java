package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface GameStatisticsRepository extends JpaRepository<GameStatisticsJpaEntity, Integer> {
    Optional<GameStatisticsJpaEntity> findByPlayerIdAndGameId(UUID player, UUID gameId);

    List<GameStatisticsJpaEntity> findByGameId(UUID gameId);
}
