package be.kdg.prog6.storeContext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StoreGameJpaRepository extends JpaRepository<StoreGameJpaEntity, UUID> {
    @Query("SELECT sg FROM StoreGameJpaEntity sg LEFT JOIN FETCH sg.reviews")
    List<StoreGameJpaEntity> findAll();

}
