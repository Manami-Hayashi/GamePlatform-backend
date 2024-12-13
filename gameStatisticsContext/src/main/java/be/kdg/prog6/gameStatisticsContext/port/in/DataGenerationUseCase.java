package be.kdg.prog6.gameStatisticsContext.port.in;


public interface DataGenerationUseCase {
    void generatePlayers(int count);
    void generateGameStatistics(int gamesPerPlayer);
}
