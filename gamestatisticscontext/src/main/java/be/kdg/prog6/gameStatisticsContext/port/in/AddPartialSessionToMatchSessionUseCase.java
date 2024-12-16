package be.kdg.prog6.gameStatisticsContext.port.in;

@FunctionalInterface
public interface AddPartialSessionToMatchSessionUseCase {
    void addPartialSession(AddPartialSessionCommand command);
}
