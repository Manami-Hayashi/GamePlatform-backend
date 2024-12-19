package be.kdg.prog6.gameManagementContext;

import be.kdg.prog6.gameManagementContext.domain.GameId;

import java.math.BigDecimal;
import java.util.UUID;

public class TestIds {
    public static final GameId GAME_ID = new GameId(UUID.fromString("96712d0d-dd10-4802-9d62-1d30fa638422"));
    public static final String GAME_NAME = "Test Game";
    public static final BigDecimal GAME_PRICE = BigDecimal.valueOf(59.99);
    public static final String GAME_DESCRIPTION = "A test game description";

    private TestIds() {
    }
}