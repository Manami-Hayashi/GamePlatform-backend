package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.*;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerCreatedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class PlayerDBAdapter implements PlayerCreatedPort, PlayerLoadedPort, UpdatePlayerPort {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDBAdapter.class);
    private final PlayerJpaRepository playerJpaRepository;

    public PlayerDBAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public void createPlayer(Player player) {
       PlayerJpaEntity jpaEntity = new PlayerJpaEntity(
               player.getPlayerId().id(),
               player.getName()
       );

       logger.info("creating new Player with name {}", player.getName());

       playerJpaRepository.save(jpaEntity);

    }



    @Override
    public Player loadPlayerByName(String name) {
        // Find the player by name (assuming this method exists in the repository)
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByName(name);

        // Convert the PlayerJpaEntity to Player domain object
        return toPlayer(playerJpaEntity);
    }

    @Override
    public Player loadPlayer(UUID playerId) {
        // Find the player by id (assuming this method exists in the repository)
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByPlayerId(playerId).orElse(null);
        logger.info("loading player with id {}", playerId);
        if (playerJpaEntity == null) {
            throw new IllegalArgumentException("Player not found");
        }
        return toPlayer(playerJpaEntity);
    }

    @Override
    public void updatePlayer(Player player) {
        // Convert domain objects to JPA entities
        List<FriendJpaEntity> friendJpaEntities = player.getFriends()
                .stream()
                .map(this::toFriendJpaEntity)
                .toList();

        List<GameOwnedJpaEntity> gameOwnedJpaEntities = player.getGamesOwned()
                .stream()
                .map(this::toGameOwnedJpaEntity)
                .toList();

        // Create a JPA entity with updated data
        PlayerJpaEntity jpaEntity = new PlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName(),
                friendJpaEntities,
                gameOwnedJpaEntities
        );
        logger.info("Friends: {}", player.getFriends());
        logger.info("Games: {}", player.getGamesOwned());

        // Set relationships for child entities
        friendJpaEntities.forEach(friend -> friend.setPlayer(jpaEntity));
        gameOwnedJpaEntities.forEach(game -> game.setPlayer(jpaEntity));

        // Save the updated player
        logger.info("saving player with id {}", player.getPlayerId().id());
        playerJpaRepository.save(jpaEntity);
    }


    // Method to map PlayerJpaEntity to Player domain object
    private Player toPlayer(final PlayerJpaEntity playerJpaEntity) {
        if (playerJpaEntity == null) {
            return null;  // or throw an exception if you want to handle this case
        }

        Player player = new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getName()
        );


        // Map gamesOwned from JPA to domain objects
        List<Game> games = playerJpaEntity.getGameOwned() != null
                ? playerJpaEntity.getGameOwned().stream()
                .map(this::toGame)
                .toList()
                : new ArrayList<>();

        // Set the gamesOwned list in the Player domain object
        player.setGamesOwned(games);

        List<Friend> friends = playerJpaEntity.getFriends() != null
                ? playerJpaEntity.getFriends().stream()
                .map(this::toFriend)
                .toList()
                : new ArrayList<>();

        player.setFriends(friends);

        return player;
    }


    private Friend toFriend(FriendJpaEntity friendJpaEntity) {
        return new Friend(
                new PlayerId(friendJpaEntity.getFriendId()),
                friendJpaEntity.isFavorite()
        );
    }

    private FriendJpaEntity toFriendJpaEntity(Friend friend) {
        return new FriendJpaEntity(
                friend.getFriendId().id(),
                friend.isFavorite()
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
