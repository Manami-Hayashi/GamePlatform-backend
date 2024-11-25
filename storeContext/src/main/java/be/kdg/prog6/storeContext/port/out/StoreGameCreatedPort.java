package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.StoreGame;

public interface StoreGameCreatedPort {
    void createStoreGame(StoreGame newStoreGame);

}
