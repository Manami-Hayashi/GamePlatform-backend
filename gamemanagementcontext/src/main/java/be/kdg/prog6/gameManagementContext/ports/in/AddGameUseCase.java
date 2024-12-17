package be.kdg.prog6.gameManagementContext.ports.in;

@FunctionalInterface
public interface AddGameUseCase {
    void addGame(AddGameCommand command);
}