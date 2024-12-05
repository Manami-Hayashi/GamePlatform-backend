package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AddFriendUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.CreateFriendPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.springframework.stereotype.Service;

@Service
public class AddFriendUseCaseImpl implements AddFriendUseCase {
    private final PlayerLoadedPort playerLoadedPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final CreateFriendPort createFriendPort;

    public AddFriendUseCaseImpl(PlayerLoadedPort playerLoadedPort, UpdatePlayerPort updatePlayerPort, CreateFriendPort createFriendPort) {
        this.playerLoadedPort = playerLoadedPort;
        this.updatePlayerPort = updatePlayerPort;
        this.createFriendPort = createFriendPort;
    }

    @Override
    public void addFriend(PlayerId playerId, PlayerId player2Id) {
        Player player = playerLoadedPort.loadPlayer(playerId.id());
        Player player2 = playerLoadedPort.loadPlayer(player2Id.id());
        Friend friend = new Friend(player2.getPlayerId(), player2.getName());
        createFriendPort.createFriend(friend, player);
        player.addFriend(friend);
        updatePlayerPort.updatePlayer(player);
    }
}
