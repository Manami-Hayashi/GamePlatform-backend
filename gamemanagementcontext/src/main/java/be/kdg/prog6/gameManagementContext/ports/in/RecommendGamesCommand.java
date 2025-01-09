package be.kdg.prog6.gameManagementContext.ports.in;

import java.util.List;

public record RecommendGamesCommand(List<String> gameNames) {
}
