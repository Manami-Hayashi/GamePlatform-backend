package be.kdg.prog6.storeContext.adapters.out.db;

import be.kdg.prog6.storeContext.domain.Player;
import be.kdg.prog6.storeContext.port.out.PlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class StorePlayerDBAdapter implements PlayerCreatedPort {
    private static final Logger logger = LoggerFactory.getLogger(StorePlayerDBAdapter.class);
    private final StorePlayerRepository storePlayerRepository;

    public StorePlayerDBAdapter(StorePlayerRepository storePlayerRepository) {
        this.storePlayerRepository = storePlayerRepository;
    }

    @Override
    public void createPlayer(Player player) {
        StorePlayerJpaEntity jpaEntity = new StorePlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );
        logger.info("creating new Player with gameName on store db {}", player.getName());
        // save to database
        storePlayerRepository.save(jpaEntity);
    }
}
