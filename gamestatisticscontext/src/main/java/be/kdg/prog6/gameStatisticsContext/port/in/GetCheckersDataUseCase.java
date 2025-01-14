package be.kdg.prog6.gameStatisticsContext.port.in;

@FunctionalInterface
public interface GetCheckersDataUseCase {
    void getCheckersData(GetCheckersDataCommand command);
}
