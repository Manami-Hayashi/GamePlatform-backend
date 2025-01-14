package be.kdg.prog6.gameManagementContext.ports.in;

import java.util.List;

@FunctionalInterface
public interface RecommendGamesUseCase {
    List<String> recommendGames(RecommendGamesCommand command);
}
