package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.DisplayGameCatalogUseCase;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
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
    public List<StoreGame> getAvailableGames() {
        logger.info("Getting all games from the store");

        return loadStoreGamePort.findAll();
    }

}
