package be.kdg.prog6.storeContext.adapters.in;

import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.DisplayGameCatalogUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/store")
public class StoreGameController {
    private final DisplayGameCatalogUseCase displayGameCatalogUseCase;

    public StoreGameController(DisplayGameCatalogUseCase displayGameCatalogUseCase) {
        this.displayGameCatalogUseCase = displayGameCatalogUseCase;
    }

    @GetMapping("/games")
    public ResponseEntity<List<StoreGameDto>> getAvailableGames() {
        List<StoreGame> availableGames = displayGameCatalogUseCase.getAvailableGames();

        if (availableGames.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<StoreGameDto> storeGames = availableGames.stream()
                .map(StoreGameDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(storeGames);
    }


}
