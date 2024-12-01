package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import java.util.List;

public interface GetFriendsUseCase {
    List<Player> getFriends();
}