package be.kdg.storeContext.port.out;

import be.kdg.storeContext.domain.StoreGame;

public interface StoreGameCreatedPort {
    void createStoreGame(StoreGame newStoreGame);

}
