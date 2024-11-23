package be.kdg.storeContext.port.out;

import be.kdg.storeContext.domain.StoreGame;

import java.util.List;

public interface LoadStoreGamePort {
    List<StoreGame> findAll();
}
