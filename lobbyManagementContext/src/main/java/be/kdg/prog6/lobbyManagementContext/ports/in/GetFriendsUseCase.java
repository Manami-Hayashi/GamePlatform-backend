package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import java.util.List;
import java.util.UUID;

public interface GetFriendsUseCase {
    List<Player> getFriends(UUID playerId);
}
