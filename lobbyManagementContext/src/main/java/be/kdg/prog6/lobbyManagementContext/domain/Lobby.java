package be.kdg.prog6.lobbyManagementContext.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {
    private final UUID lobbyId;
    private List<PlayerId> players;
    private static final int MAX_PLAYERS = 2;

    public Lobby(UUID lobbyId, List<PlayerId> players) {
        this.lobbyId = lobbyId;
        this.players = players;
    }

    public Lobby() {
        this.lobbyId = UUID.randomUUID();
        this.players = new ArrayList<>();
    }

    public boolean isFull() {
        return players.size() != MAX_PLAYERS;
    }

    public void inviteFriend(PlayerId playerId, PlayerId friendId) {
        if (players.contains(playerId) && players.size() < MAX_PLAYERS) {
            players.add(friendId);
        }
    }

    public void matchWithRandomPlayer(PlayerId playerId, List<PlayerId> existingPlayers) {
        if (players.contains(playerId) && players.size() < MAX_PLAYERS) {
            for (PlayerId existingPlayer : existingPlayers) {
                if (!players.contains(existingPlayer)) {
                    players.add(existingPlayer);
                    return;
                }
            }
            players.add(new PlayerId(UUID.randomUUID()));
        }
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public List<PlayerId> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerId> players) {
        this.players = players;
    }

    public void addPlayer(PlayerId playerId) {
        if (isFull() && !players.contains(playerId)) {
            players.add(playerId);
        }
    }
}