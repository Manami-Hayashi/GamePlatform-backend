package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchSessionRepository extends JpaRepository<MatchSessionJpaEntity, Integer> {
    List<MatchSessionJpaEntity> findAllByGameStatisticsIn(List<GameStatisticsJpaEntity> gameStatistics);
}
