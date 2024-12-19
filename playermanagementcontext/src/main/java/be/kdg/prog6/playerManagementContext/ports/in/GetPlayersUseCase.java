package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.Player;

import java.util.List;

public interface GetPlayersUseCase {
    List<Player> getPlayers();
}
