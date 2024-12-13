package be.kdg.prog6.gameStatisticsContext.port.in;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetMatchHistoryUseCase {
    List<GetMatchHistoryCommand> getMatchHistory(UUID playerId);
}
