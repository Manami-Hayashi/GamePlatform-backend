package be.kdg.prog6.storeContext.adapter.in;

import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.DisplayGameCatalogUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreGameController {
    private final DisplayGameCatalogUseCase displayGameCatalogUseCase;

    public StoreGameController(DisplayGameCatalogUseCase displayGameCatalogUseCase) {
        this.displayGameCatalogUseCase = displayGameCatalogUseCase;
    }

    @GetMapping
    public List<StoreGame> getAvailableGames() {
        return displayGameCatalogUseCase.getAvailableGames();
    }


}
