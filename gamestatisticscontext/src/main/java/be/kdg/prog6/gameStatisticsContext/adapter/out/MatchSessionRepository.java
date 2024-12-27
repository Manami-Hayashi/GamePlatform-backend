package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MatchSessionRepository extends JpaRepository<MatchSessionJpaEntity, UUID> {
    List<MatchSessionJpaEntity> findAllByGameStatisticsIn(List<GameStatisticsJpaEntity> gameStatistics);
}
