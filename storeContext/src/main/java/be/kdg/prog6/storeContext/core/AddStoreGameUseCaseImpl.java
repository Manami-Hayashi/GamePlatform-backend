package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.AddStoreGameUseCase;
import be.kdg.prog6.storeContext.port.out.StoreGameCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddStoreGameUseCaseImpl implements AddStoreGameUseCase {
    private final StoreGameCreatedPort storeGameCreatedPort;
    private static final Logger logger = LoggerFactory.getLogger(AddStoreGameUseCaseImpl.class);

    public AddStoreGameUseCaseImpl(StoreGameCreatedPort storeGameCreatedPort) {
        this.storeGameCreatedPort = storeGameCreatedPort;
    }

    @Override
    public void addStoreGame(StoreGame storeGame) {
        logger.info("Adding new game to the store: {}", storeGame.getName());
        storeGameCreatedPort.createStoreGame(storeGame);
    }
}