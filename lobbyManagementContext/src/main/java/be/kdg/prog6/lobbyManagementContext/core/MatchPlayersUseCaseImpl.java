package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


//check if player is online

@Service
public class MatchPlayersUseCaseImpl implements MatchPlayersUseCase {
    private static final Logger logger = LoggerFactory.getLogger(MatchPlayersUseCaseImpl.class);

    private final LoadAllLobbiesPort loadAllLobbiesPort;
    private final SaveLobbyPort saveLobbyPort;

    public MatchPlayersUseCaseImpl(LoadAllLobbiesPort loadAllLobbiesPort, SaveLobbyPort saveLobbyPort) {
        this.loadAllLobbiesPort = loadAllLobbiesPort;
        this.saveLobbyPort = saveLobbyPort;
    }

    @Override
    @Transactional
    public void matchPlayers(MatchPlayersCommand command) {
        UUID playerId = command.playerId();
        UUID friendId = command.friendId();

        logger.info("Matching player with ID: {} and friend ID: {}", playerId, friendId);

        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies();
        List<Player> allPlayers = lobbies.stream()
                .flatMap(lobby -> lobby.getPlayers().stream())
                .toList();

        Player player = allPlayers.stream()
                .filter(p -> p.getPlayerId().id().equals(playerId))
                .findFirst()
                .orElse(null);

        if (player == null || !player.isOnline()) {
            logger.info("Player with ID: {} is not online or does not exist.", playerId);
            return;
        }

        Player friend = null;
        if (friendId != null) {
            friend = allPlayers.stream()
                    .filter(p -> p.getPlayerId().id().equals(friendId))
                    .findFirst()
                    .orElse(null);

            if (friend == null || !friend.isOnline()) {
                logger.info("Friend with ID: {} is not online or does not exist.", friendId);
                return;
            }
        }

        boolean playerInLobby = allPlayers.stream().anyMatch(p -> p.getPlayerId().id().equals(playerId));
        boolean friendInLobby = friendId != null && allPlayers.stream().anyMatch(p -> p.getPlayerId().id().equals(friendId));

        if (!playerInLobby && !friendInLobby) {
            if (friendId != null) {
                logger.info("Neither player nor friend is in any lobby. Creating a private lobby for them.");
                createPrivateLobby(player, friend);
            } else {
                logger.info("Player is not in any lobby. Matching with an available lobby.");
                addPlayersToLobby(lobbies, player, null);
            }
        } else if (playerInLobby && !friendInLobby && friend != null) {
            logger.info("Player is in a lobby. Inviting friend to the same lobby.");
            inviteFriendToLobby(lobbies, player, friend);
        } else {
            logger.info("Player or friend is already in a lobby.");
            addPlayersToLobby(lobbies, player, friend);
        }
    }

    private void createPrivateLobby(Player player, Player friend) {
        Lobby newLobby = new Lobby();
        newLobby.addPlayer(player);
        if (friend != null) {
            newLobby.inviteFriend(player, friend);
        }
        saveLobbyPort.saveLobby(newLobby);
    }

    private void addPlayersToLobby(List<Lobby> lobbies, Player player, Player friend) {
        Lobby availableLobby = lobbies.stream()
                .filter(lobby -> !lobby.isFull())
                .findFirst()
                .orElse(null);

        if (availableLobby == null) {
            logger.info("No available lobby found, creating a new lobby.");
            availableLobby = new Lobby();
        } else {
            logger.info("Found an available lobby with ID: {}", availableLobby.getLobbyId());
        }

        availableLobby.addPlayer(player);
        if (friend != null) {
            availableLobby.addPlayer(friend);
        } else {
            List<Player> existingPlayers = lobbies.stream()
                    .flatMap(lobby -> lobby.getPlayers().stream())
                    .filter(p -> !p.getPlayerId().id().equals(player.getPlayerId().id()))
                    .collect(Collectors.toList());
            availableLobby.matchWithRandomPlayer(player, existingPlayers);
        }

        saveLobbyPort.saveLobby(availableLobby);
    }

    private void inviteFriendToLobby(List<Lobby> lobbies, Player player, Player friend) {
        Lobby playerLobby = lobbies.stream()
                .filter(lobby -> lobby.getPlayers().contains(player))
                .findFirst()
                .orElse(null);

        if (playerLobby != null) {
            playerLobby.inviteFriend(player, friend);
            saveLobbyPort.saveLobby(playerLobby);
        }
    }
}


//package be.kdg.prog6.lobbyManagementContext.core;
//
//import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
//import be.kdg.prog6.lobbyManagementContext.domain.Player;
//import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
//import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
//import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersUseCase;
//import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
//import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
//import jakarta.transaction.Transactional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class MatchPlayersUseCaseImpl implements MatchPlayersUseCase {
//    private static final Logger logger = LoggerFactory.getLogger(MatchPlayersUseCaseImpl.class);
//
//    private final LoadAllLobbiesPort loadAllLobbiesPort;
//    private final SaveLobbyPort saveLobbyPort;
//
//    public MatchPlayersUseCaseImpl(LoadAllLobbiesPort loadAllLobbiesPort, SaveLobbyPort saveLobbyPort) {
//        this.loadAllLobbiesPort = loadAllLobbiesPort;
//        this.saveLobbyPort = saveLobbyPort;
//    }
//
//    @Override
//    @Transactional
//    public void matchPlayers(MatchPlayersCommand command) {
//        UUID playerId = command.playerId();
//        UUID friendId = command.friendId();
//
//        logger.info("Matching player with ID: {} and friend ID: {}", playerId, friendId);
//
//        List<Lobby> lobbies = loadAllLobbiesPort.loadAllLobbies();
//        List<Player> allPlayers = lobbies.stream()
//                .flatMap(lobby -> lobby.getPlayers().stream())
//                .toList();
//
//        boolean playerInLobby = allPlayers.stream().anyMatch(player -> player.getPlayerId().id().equals(playerId));
//        boolean friendInLobby = friendId != null && allPlayers.stream().anyMatch(player -> player.getPlayerId().id().equals(friendId));
//
//        if (!playerInLobby && !friendInLobby) {
//            if (friendId != null) {
//                logger.info("Neither player nor friend is in any lobby. Creating a private lobby for them.");
//                createPrivateLobby(playerId, friendId);
//            } else {
//                logger.info("Player is not in any lobby. Matching with an available lobby.");
//                addPlayersToLobby(lobbies, playerId, null);
//            }
//        } else {
//            logger.info("Player or friend is already in a lobby.");
//            addPlayersToLobby(lobbies, playerId, friendId);
//        }
//    }
//
//    private void createPrivateLobby(UUID playerId, UUID friendId) {
//        Lobby newLobby = new Lobby();
//        newLobby.addPlayer(new Player(new PlayerId(playerId), "PlayerName")); // Replace "PlayerName" with actual player name
//        if (friendId != null) {
//            newLobby.addPlayer(new Player(new PlayerId(friendId), "FriendName")); // Replace "FriendName" with actual friend name
//        }
//        saveLobbyPort.saveLobby(newLobby);
//    }
//
//    private void addPlayersToLobby(List<Lobby> lobbies, UUID playerId, UUID friendId) {
//        Lobby availableLobby = lobbies.stream()
//                .filter(lobby -> !lobby.isFull())
//                .findFirst()
//                .orElse(null);
//
//        if (availableLobby == null) {
//            logger.info("No available lobby found, creating a new lobby.");
//            availableLobby = new Lobby();
//        } else {
//            logger.info("Found an available lobby with ID: {}", availableLobby.getLobbyId());
//        }
//
//        availableLobby.addPlayer(new Player(new PlayerId(playerId), "PlayerName")); // Replace "PlayerName" with actual player name
//        if (friendId != null) {
//            availableLobby.addPlayer(new Player(new PlayerId(friendId), "FriendName")); // Replace "FriendName" with actual friend name
//        } else {
//            List<Player> existingPlayers = lobbies.stream()
//                    .flatMap(lobby -> lobby.getPlayers().stream())
//                    .filter(player -> !player.getPlayerId().id().equals(playerId))
//                    .collect(Collectors.toList());
//            availableLobby.matchWithRandomPlayer(new Player(new PlayerId(playerId), "PlayerName"), existingPlayers); // Replace "PlayerName" with actual player name
//        }
//
//        saveLobbyPort.saveLobby(availableLobby);
//    }
//}