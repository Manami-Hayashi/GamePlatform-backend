package be.kdg.prog6.lobbyManagementContext.domain;

import java.util.List;
import java.util.UUID;

public class Lobby {
    private final UUID lobbyId;
    private  List<PlayerId> players;
    private static final int MAX_PLAYERS = 2;

    public Lobby(UUID lobbyId, List<PlayerId> players) {
        this.lobbyId = lobbyId;
        this.players = players;
    }

    public boolean isFull() {
        return players.size() == MAX_PLAYERS;
    }

    public void inviteFriend(PlayerId playerId, PlayerId friendId) {
        if (players.contains(playerId) && players.size() < MAX_PLAYERS) {
            players.add(friendId);
        }
    }

    public void matchWithRandomPlayer(PlayerId playerId) {
        if (players.contains(playerId) && players.size() < MAX_PLAYERS) {
            players.add(new PlayerId(UUID.randomUUID()));
        }
    }
}
