package be.kdg.prog6.storeContext.port.in;

import be.kdg.prog6.storeContext.domain.StoreGame;

import java.util.List;

public interface DisplayGameCatalogUseCase {
    List<StoreGame> getAvailableGames();
}
