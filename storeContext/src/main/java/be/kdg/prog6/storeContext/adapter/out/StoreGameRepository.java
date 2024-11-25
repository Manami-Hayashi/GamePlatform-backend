package be.kdg.prog6.storeContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreGameRepository extends JpaRepository<StoreGameJpaEntity, UUID> {
}
