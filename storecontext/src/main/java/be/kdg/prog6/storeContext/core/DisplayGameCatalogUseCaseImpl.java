package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.DisplayGameCatalogUseCase;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayGameCatalogUseCaseImpl implements DisplayGameCatalogUseCase {

    private final LoadStoreGamePort loadStoreGamePort;
    private static final Logger logger = LoggerFactory.getLogger(DisplayGameCatalogUseCaseImpl.class);

    public DisplayGameCatalogUseCaseImpl(LoadStoreGamePort loadStoreGamePort) {
        this.loadStoreGamePort = loadStoreGamePort;
    }

    @Override
    @Transactional
    public List<StoreGame> getAvailableGames() {
        List<StoreGame> storeGames;
        try {
            storeGames = loadStoreGamePort.findAll();
        } catch (Exception e) {
            logger.error("Error while loading games", e);
            throw new IllegalArgumentException("Error while loading games");
        }
        if (storeGames.isEmpty()) {
            logger.error("No games found");
            throw new IllegalArgumentException("No games found");
        }
        return storeGames;
    }
}
