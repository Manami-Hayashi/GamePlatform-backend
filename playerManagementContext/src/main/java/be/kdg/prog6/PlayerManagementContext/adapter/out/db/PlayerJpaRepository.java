package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, String> {

    PlayerJpaEntity findByName(String name);
}
