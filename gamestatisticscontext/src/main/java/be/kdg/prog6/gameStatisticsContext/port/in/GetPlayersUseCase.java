package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.Player;

import java.util.List;

public interface GetPlayersUseCase {
    List<Player> getPlayers();
}
