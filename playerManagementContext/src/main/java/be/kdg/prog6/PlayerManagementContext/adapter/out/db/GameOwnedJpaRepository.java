package be.kdg.prog6.PlayerManagementContext.adapter.out.db;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("playerGameJpaRepository")
public interface GameOwnedJpaRepository extends JpaRepository<GameOwnedJpaEntity, UUID> {

}
