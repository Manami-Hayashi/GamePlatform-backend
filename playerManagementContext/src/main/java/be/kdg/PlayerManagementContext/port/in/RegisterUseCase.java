package be.kdg.PlayerManagementContext.port.in;

@FunctionalInterface
public interface RegisterUseCase {
    void registerPlayer(RegisterUserCommand command);
}
