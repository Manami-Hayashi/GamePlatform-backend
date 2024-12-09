package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.*;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayersPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerCreatedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class PlayerDbAdapter implements PlayerCreatedPort, PlayerLoadedPort, LoadPlayersPort, UpdatePlayerPort {

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

       LOGGER.info("creating new Player with name {}", player.getName());

       playerJpaRepository.save(jpaEntity);

    }

    @Transactional
    @Override
    public Player loadPlayerByName(String name) {
        // Find the player by name (assuming this method exists in the repository)
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByName(name);

        // Convert the PlayerJpaEntity to Player domain object
        return toPlayer(playerJpaEntity);
    }

    @Transactional
    @Override
    public Player loadPlayer(UUID playerId) {
        // Find the player by id (assuming this method exists in the repository)
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByPlayerId(playerId).orElse(null);
        LOGGER.info("loading player with id {}", playerId);
        if (playerJpaEntity == null) {
            throw new IllegalArgumentException("Player not found");
        }
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
        // Retrieve the existing player JPA entity
        PlayerJpaEntity existingJpaEntity = playerJpaRepository.findById(player.getPlayerId().id())
                .orElseThrow(() -> new EntityNotFoundException("Player not found with ID: " + player.getPlayerId().id()));

        // Update basic fields
        existingJpaEntity.setName(player.getName());

        // Convert domain objects to JPA entities for friends
        List<FriendJpaEntity> updatedFriends = player.getFriends()
                .stream()
                .map(this::toFriendJpaEntity)
                .toList();

        // Convert domain objects to JPA entities for games
        List<GameOwnedJpaEntity> updatedGames = player.getGamesOwned()
                .stream()
                .map(this::toGameOwnedJpaEntity)
                .toList();

        LOGGER.info("Updated Friends: {}", updatedFriends);
        LOGGER.info("Updated Games: {}", updatedGames);

        existingJpaEntity.setFriends(updatedFriends);
        existingJpaEntity.setGamesOwned(updatedGames);

        // Save the updated player
        LOGGER.info("Saving player with ID {}", player.getPlayerId().id());
        playerJpaRepository.save(existingJpaEntity);
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

        List<Game> games = playerJpaEntity.getGameOwned() != null
                ? playerJpaEntity.getGameOwned().stream().map(this::toGame).toList()
                : new ArrayList<>();
        player.setGamesOwned(games);

        List<Friend> friends = playerJpaEntity.getFriends() != null
                ? playerJpaEntity.getFriends().stream().map(this::toFriend).toList()
                : new ArrayList<>();
        player.setFriends(friends);

        return player;
    }


    private Friend toFriend(FriendJpaEntity friendJpaEntity) {
        return new Friend(
                new PlayerId(friendJpaEntity.getPlayer().getPlayerId()),
                friendJpaEntity.getName(),
                friendJpaEntity.isFavorite(),
                FriendRequestStatus.valueOf(friendJpaEntity.getFriendRequestStatus())
        );
    }

    private FriendJpaEntity toFriendJpaEntity(Friend friend) {
        return new FriendJpaEntity(
                friend.getFriendId().id(),
                friend.getName(),
                friend.isFavorite(),
                friend.getFriendRequestStatus().toString()
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
