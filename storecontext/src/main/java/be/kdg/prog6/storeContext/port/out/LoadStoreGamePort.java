package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.StoreGame;

import java.util.List;

public interface LoadStoreGamePort {
    List<StoreGame> findAll();

    StoreGame findById(GameId gameId);
}
