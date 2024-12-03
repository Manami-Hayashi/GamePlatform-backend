package be.kdg.prog6.gameStatisticsContext.port.in;

@FunctionalInterface
public interface UpdateGameStatisticsUseCase {
    void updateGameStatistics(UpdateGameStatisticsCommand updateGameStatisticsCommand);
}
