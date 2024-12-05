package be.kdg.prog6.storeContext.adapters.out.db;

import be.kdg.prog6.storeContext.adapters.out.db.StorePlayerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StorePlayerRepository extends JpaRepository<StorePlayerJpaEntity, UUID> {

}
