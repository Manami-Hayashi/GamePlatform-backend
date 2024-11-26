package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class Player {
    private final PlayerId id;
    private final String name;
    private int age;
    private Gender gender;
    private String location;
    private List<GameStatistics> gameStatistics;

    public Player(PlayerId id, String name, int age, Gender gender, String location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public Player(PlayerId id, String name, int age, Gender gender, String location, List<GameStatistics> gameStatistics) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
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
}
