package be.kdg.prog6.gameManagementContext;

import java.math.BigDecimal;
import java.util.UUID;

public class TestIds {
    public static final UUID PLAYER_ID = UUID.fromString("e4e685be-ed89-42fb-a681-f272149c8218");
    public static final String GAME_NAME = "Test Game";
    public static final BigDecimal GAME_PRICE = BigDecimal.valueOf(59.99);
    public static final String GAME_DESCRIPTION = "A test game description";

    private TestIds() {
    }
}
