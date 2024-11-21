package be.kdg.PlayerManagementContext.adapter.out.db;

import be.kdg.PlayerManagementContext.domain.Player;
import be.kdg.PlayerManagementContext.domain.PlayerId;
import be.kdg.PlayerManagementContext.port.out.PlayerCreatedPort;
import be.kdg.PlayerManagementContext.port.out.PlayerLoadedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class PlayerDBAdapter implements PlayerCreatedPort, PlayerLoadedPort {

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

    // Method to map PlayerJpaEntity to Player domain object
    private Player toPlayer(final PlayerJpaEntity playerJpaEntity) {
        if (playerJpaEntity == null) {
            return null;  // or throw an exception if you want to handle this case
        }

        // Create PlayerId using the playerId from PlayerJpaEntity
        PlayerId playerId = new PlayerId(playerJpaEntity.getPlayerId());

        // Map fields to Player object
        return new Player(
                playerId,
                playerJpaEntity.getName()
        );
    }
}
