package be.kdg.PlayerManagementContext.port.out;
import be.kdg.PlayerManagementContext.domain.Player;

@FunctionalInterface
public interface PlayerLoadedPort {
    Player loadPlayerByName(String name);
}
