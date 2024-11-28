package be.kdg.prog6.lobbyManagementContext.adapters.out;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.ports.out.LobbyPlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LobbyDBAdapter implements LobbyPlayerCreatedPort {
    private static final Logger logger = LoggerFactory.getLogger(LobbyDBAdapter.class);
    private final LobbyPlayerJpaRepository lobbyPlayerRepository;

    public LobbyDBAdapter(LobbyPlayerJpaRepository lobbyPlayerRepository) {
        this.lobbyPlayerRepository = lobbyPlayerRepository;
    }

    @Override
    public void createPlayer(Player player) {
        LobbyPlayerJpaEntity jpaEntity = new LobbyPlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );
        logger.info("creating new Player with name {}", player.getName());
        // save to database
        lobbyPlayerRepository.save(jpaEntity);
    }
}
