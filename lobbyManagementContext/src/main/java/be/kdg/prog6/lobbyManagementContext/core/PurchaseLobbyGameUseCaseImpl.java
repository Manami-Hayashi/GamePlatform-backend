package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.PurchaseLobbyGameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LobbyGamePurchasedPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PurchaseLobbyGameUseCaseImpl implements PurchaseLobbyGameUseCase {
    private final LobbyGamePurchasedPort lobbyGamePurchasedPort;

    public PurchaseLobbyGameUseCaseImpl(LobbyGamePurchasedPort lobbyGamePurchasedPort) {
        this.lobbyGamePurchasedPort = lobbyGamePurchasedPort;
    }

    @Override
    @Transactional
    public void purchaseLobbyGame(PlayerId playerId, Game game) {
        lobbyGamePurchasedPort.purchaseLobbyGame(playerId, game);
    }

}
