package be.kdg.prog6.PlayerManagementContext.port.out;
import be.kdg.prog6.PlayerManagementContext.domain.Player;

import java.util.UUID;

public interface PlayerLoadedPort {
    Player loadPlayerByName(String name);

    Player loadPlayer(UUID id);
}
