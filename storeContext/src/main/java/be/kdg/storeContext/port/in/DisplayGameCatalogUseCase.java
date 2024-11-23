package be.kdg.storeContext.port.in;

import be.kdg.storeContext.domain.StoreGame;

import java.util.List;

public interface DisplayGameCatalogUseCase {
    List<StoreGame> getAvailableGames();
}
