package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.*;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayersPort;
import be.kdg.prog6.PlayerManagementContext.port.out.CreatePlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class PlayerDbAdapter implements CreatePlayerPort, LoadPlayerPort, LoadPlayersPort, UpdatePlayerPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDbAdapter.class);
    private final PlayerJpaRepository playerJpaRepository;

    public PlayerDbAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Transactional
    @Override
    public void createPlayer(Player player) {
        PlayerJpaEntity jpaEntity = new PlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );

        LOGGER.info("Creating new player with name {}", player.getName());
        playerJpaRepository.save(jpaEntity);
    }

    @Transactional
    @Override
    public Player loadPlayer(UUID playerId) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        return toPlayer(playerJpaEntity);
    }

    @Transactional
    @Override
    public List<Player> loadPlayers() {
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.findAll();
        return playerJpaEntities.stream()
                .map(this::toPlayer)
                .toList();
    }

    @Transactional
    @Override
    public void updatePlayer(Player player) {
        LOGGER.info("Updating player with ID: {}", player.getPlayerId().id());

        // Fetch existing player entity
        PlayerJpaEntity existingEntity = playerJpaRepository.findByPlayerId(player.getPlayerId().id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        // Update fields
        existingEntity.setName(player.getName());

        // Update friends initiated
        existingEntity.getFriendsInitiated().clear();
        if (player.getFriendsInitiated() != null) {
            existingEntity.getFriendsInitiated().addAll(player.getFriendsInitiated().stream().map(this::toFriendJpaEntity).toList());
        }

        // Update friends received
        existingEntity.getFriendsReceived().clear();
        if (player.getFriendsReceived() != null) {
            existingEntity.getFriendsReceived().addAll(player.getFriendsReceived().stream().map(this::toFriendJpaEntity).toList());
        }

        // Update games owned
        existingEntity.getGameOwned().clear();
        if (player.getGamesOwned() != null) {
            existingEntity.getGameOwned().addAll(player.getGamesOwned().stream().map(this::toGameOwnedJpaEntity).toList());
        }

        // Persist updated player
        playerJpaRepository.save(existingEntity);
        LOGGER.info("Successfully updated player with ID: {}", player.getPlayerId().id());
    }

    // Method to map PlayerJpaEntity to Player domain object
    private Player toPlayer(PlayerJpaEntity playerJpaEntity) {
        if (playerJpaEntity == null) {
            return null;
        }

        Player player = new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getName()
        );

        // Map friends initiated
        List<Friend> friendsInitiated = playerJpaEntity.getFriendsInitiated() != null
                ? playerJpaEntity.getFriendsInitiated().stream().map(this::toFriend).toList()
                : new ArrayList<>();
        player.setFriendsInitiated(friendsInitiated);

        // Map friends received
        List<Friend> friendsReceived = playerJpaEntity.getFriendsReceived() != null
                ? playerJpaEntity.getFriendsReceived().stream().map(this::toFriend).toList()
                : new ArrayList<>();
        player.setFriendsReceived(friendsReceived);

        // Map games owned
        List<Game> games = playerJpaEntity.getGameOwned() != null
                ? playerJpaEntity.getGameOwned().stream().map(this::toGame).toList()
                : new ArrayList<>();
        player.setGamesOwned(games);

        return player;
    }

    private Friend toFriend(FriendJpaEntity friendJpaEntity) {
        if (friendJpaEntity == null) {
            return null;
        }
        return new Friend(
                new Player(new PlayerId(friendJpaEntity.getRequester().getPlayerId()), friendJpaEntity.getRequester().getName()),
                new Player(new PlayerId(friendJpaEntity.getReceiver().getPlayerId()), friendJpaEntity.getReceiver().getName()),
                FriendRequestStatus.valueOf(friendJpaEntity.getFriendRequestStatus())
        );
    }

    // Mapping Friend domain object to JPA entity
    private FriendJpaEntity toFriendJpaEntity(Friend friend) {
        if (friend == null) {
            return null;
        }
        FriendJpaEntity friendJpaEntity = new FriendJpaEntity(
                friend.getFriendRequestStatus().toString()
        );
        friendJpaEntity.setRequester(toPlayerJpaEntity(friend.getRequester()));
        friendJpaEntity.setReceiver(toPlayerJpaEntity(friend.getReceiver()));
        return friendJpaEntity;
    }

    // Mapping Player domain object to JPA entity
    private PlayerJpaEntity toPlayerJpaEntity(Player player) {
        if (player == null) {
            return null;
        }
        return new PlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );
    }

    private GameOwnedJpaEntity toGameOwnedJpaEntity(Game game) {
        return new GameOwnedJpaEntity(
                game.getGameId().id(),
                game.getGameName(),
                game.isFavorite()
        );
    }

    private Game toGame(GameOwnedJpaEntity gameOwnedJpaEntity) {
        return new Game(
                new GameId(gameOwnedJpaEntity.getGameId()),
                gameOwnedJpaEntity.getGameName(),
                gameOwnedJpaEntity.isFavorite()
        );
    }

}
