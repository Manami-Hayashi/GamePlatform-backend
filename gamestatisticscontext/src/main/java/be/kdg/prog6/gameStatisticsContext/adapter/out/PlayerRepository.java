package be.kdg.prog6.gameStatisticsContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PlayerRepository extends JpaRepository<StatsPlayerJpaEntity, UUID> {

}
