package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchSessionRepository extends JpaRepository<MatchSessionJpaEntity, Integer> {
}
