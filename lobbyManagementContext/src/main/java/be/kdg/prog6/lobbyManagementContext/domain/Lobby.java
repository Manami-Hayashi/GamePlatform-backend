package be.kdg.prog6.lobbyManagementContext.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {
    private final UUID lobbyId;
    private List<Player> players;
    private static final int MAX_PLAYERS = 2;

    public Lobby(UUID lobbyId, List<Player> players) {
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

    public void inviteFriend(Player player, Player friend) {
        if (players.contains(player) && players.size() < MAX_PLAYERS) {
            players.add(friend);
        }
    }

    public void matchWithRandomPlayer(Player player, List<Player> existingPlayers) {
        if (players.contains(player) && players.size() < MAX_PLAYERS) {
            for (Player existingPlayer : existingPlayers) {
                if (!players.contains(existingPlayer)) {
                    players.add(existingPlayer);
                    return;
                }
            }
            players.add(new Player(new PlayerId(UUID.randomUUID()), "Random Player"));
        }
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (isFull() && !players.contains(player)) {
            players.add(player);
        }
    }

    public boolean areAllPlayersOnline() {
        return players.stream().allMatch(Player::isOnline);
    }
}