package be.kdg.prog6.storeContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, UUID> {

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.game.gameId = :gameId")
    List<ReviewJpaEntity> findByGame_GameId(UUID gameId);
    // Game_GameId is a reference to the gameId field in the GameJpaEntity class
}
