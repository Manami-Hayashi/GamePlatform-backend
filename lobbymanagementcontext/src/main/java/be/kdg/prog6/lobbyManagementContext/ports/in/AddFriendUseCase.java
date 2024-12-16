package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public interface AddFriendUseCase {
    void addFriend(UUID playerId, UUID friendId);

}
