package be.kdg.prog6.storeContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreGameJpaRepository extends JpaRepository<StoreGameJpaEntity, UUID> {
    @Query("SELECT sg FROM StoreGameJpaEntity sg LEFT JOIN FETCH sg.reviews")
    List<StoreGameJpaEntity> findAll();

    @Query("SELECT g FROM StoreGameJpaEntity g LEFT JOIN FETCH g.reviews WHERE g.gameId = :gameId")
    Optional<StoreGameJpaEntity> findByIdWithReviews(@Param("gameId") UUID gameId);
}
