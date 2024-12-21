package be.kdg.prog6.gameStatisticsContext.port.in;

@FunctionalInterface
public interface AddPartialSessionUseCase {
    void addPartialSession(AddPartialSessionCommand command);
}
