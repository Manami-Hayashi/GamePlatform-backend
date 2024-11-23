package be.kdg.storeContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreGameRepository extends JpaRepository<StoreGameJpaEntity, UUID> {
}
