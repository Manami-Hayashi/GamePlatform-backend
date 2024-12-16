package be.kdg.prog6.storeContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionJpaEntity, UUID> {
}
