package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;

public record GetCheckersDataCommand(GameId gameId) {
}
