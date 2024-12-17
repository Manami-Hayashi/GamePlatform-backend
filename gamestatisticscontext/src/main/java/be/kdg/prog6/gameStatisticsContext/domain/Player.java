package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDate;
import java.util.List;

public class Player {
    private final PlayerId id;
    private final String name;
    private LocalDate birthDate;
    private Gender gender;
    private String location;
    private List<GameStatistics> gameStatistics;

    public Player(PlayerId id, String name, LocalDate birthDate, Gender gender, String location) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
    }

    public Player(PlayerId id, String name, LocalDate birthDate, Gender gender, String location, List<GameStatistics> gameStatistics) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.location = location;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public List<GameStatistics> getGameStatistics() {
        return gameStatistics;
    }

    public int compareTo(Player player) {
        return this.gameStatistics.size() - player.gameStatistics.size();
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}
