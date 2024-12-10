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

       LOGGER.info("creating new Player with name {}", player.getName());

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

        // Map and update games
        List<GameOwnedJpaEntity> updatedGames = player.getGamesOwned().stream()
                .map(this::toGameOwnedJpaEntity)
                .toList();
        updatedGames.forEach(game -> game.setPlayer(existingEntity));

        if (player.getGamesOwned() == null) {
            player.setGamesOwned(new ArrayList<>());
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

        List<Game> games = playerJpaEntity.getGameOwned() != null
                ? playerJpaEntity.getGameOwned().stream().map(this::toGame).toList()
                : new ArrayList<>();
        player.setGamesOwned(games);

        return player;
    }


    private Friend toFriend(FriendJpaEntity friendJpaEntity) {
        return new Friend(
                FriendRequestStatus.valueOf(friendJpaEntity.getFriendRequestStatus())
        );
    }

    private FriendJpaEntity toFriendJpaEntity(Friend friend) {
        return new FriendJpaEntity(
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
