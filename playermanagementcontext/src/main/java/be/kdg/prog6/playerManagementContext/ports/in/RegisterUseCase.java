package be.kdg.prog6.playerManagementContext.ports.in;

@FunctionalInterface
public interface RegisterUseCase {
    void registerPlayer(RegisterUserCommand command);
}
