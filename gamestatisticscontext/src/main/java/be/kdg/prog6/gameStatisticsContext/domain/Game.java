package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDate;
import java.util.List;

public class Game {
    private final GameId id;
    private final String name;

    public Game(GameId id, String name) {
        this.id = id;
        this.name = name;
    }

    public GameId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
