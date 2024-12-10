package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AcceptFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayerPort;

import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AcceptFriendRequestUseCaseImpl implements AcceptFriendRequestUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptFriendRequestUseCaseImpl.class);
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public AcceptFriendRequestUseCaseImpl(LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void acceptFriendRequest(PlayerId senderId, PlayerId accepterId) {
        Player sender = loadPlayerPort.loadPlayer(senderId.id());
        Player accepter = loadPlayerPort.loadPlayer(accepterId.id());

        // ...

        updatePlayerPort.updatePlayer(sender);
        updatePlayerPort.updatePlayer(accepter);
    }
}
