package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class Player {
    private final PlayerId id;
    private final String name;
    private List<GameStatistics> gameStatistics;

    public Player(final PlayerId id, final String name, List<GameStatistics> gameStatistics) {
        this.id = id;
        this.name = name;
        this.gameStatistics = gameStatistics;
    }

    public Player(PlayerId id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GameStatistics> getGameStatistics() {
        return gameStatistics;
    }
}
