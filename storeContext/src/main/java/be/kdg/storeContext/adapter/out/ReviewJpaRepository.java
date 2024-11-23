package be.kdg.storeContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, UUID> {
    List<ReviewJpaEntity> findByGameId(UUID gameId);
}
