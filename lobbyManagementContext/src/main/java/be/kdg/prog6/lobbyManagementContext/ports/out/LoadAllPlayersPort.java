package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import java.util.List;

public interface LoadAllPlayersPort {
    List<Player> loadAllPlayers();
}