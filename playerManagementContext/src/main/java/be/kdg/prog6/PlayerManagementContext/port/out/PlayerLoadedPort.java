package be.kdg.prog6.PlayerManagementContext.port.out;
import be.kdg.prog6.PlayerManagementContext.domain.Player;

@FunctionalInterface
public interface PlayerLoadedPort {
    Player loadPlayerByName(String name);
}
