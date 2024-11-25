package be.kdg.prog6.PlayerManagementContext.port.in;

@FunctionalInterface
public interface RegisterUseCase {
    void registerPlayer(RegisterUserCommand command);
}
