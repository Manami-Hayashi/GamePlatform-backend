package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Player;

@FunctionalInterface
public interface StatsPlayerCreatedPort {
    void createPlayer(Player player);
}
