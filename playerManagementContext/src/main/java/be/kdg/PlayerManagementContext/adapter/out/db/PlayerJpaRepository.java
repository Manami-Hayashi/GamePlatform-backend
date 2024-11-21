package be.kdg.PlayerManagementContext.adapter.out.db;

import be.kdg.PlayerManagementContext.domain.Player;
import be.kdg.PlayerManagementContext.domain.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, String> {

    PlayerJpaEntity findByName(String name);
}
