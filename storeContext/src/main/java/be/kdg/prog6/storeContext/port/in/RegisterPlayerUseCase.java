package be.kdg.prog6.storeContext.port.in;

import java.util.UUID;

public interface RegisterPlayerUseCase {
    void registerPlayer(RegisterUserCommand command);
}
