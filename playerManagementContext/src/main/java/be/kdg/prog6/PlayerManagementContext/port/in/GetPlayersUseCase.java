package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.Player;

import java.util.List;

public interface GetPlayersUseCase {
    List<Player> getPlayers();
}
