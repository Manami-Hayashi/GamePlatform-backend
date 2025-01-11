package be.kdg.prog6.gameStatisticsContext.port.in;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetLeaderboardUseCase {
    List<GetLeaderboardCommand> getLeaderboard();
}
