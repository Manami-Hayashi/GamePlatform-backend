package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PlayerDbAdapter implements LoadPlayerPort {
    private final PlayerRepository playerRepository;

    public PlayerDbAdapter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> loadPlayerById(UUID playerId) {
        return playerRepository.findById(playerId)
                .map(this::toPlayer);
    }

    private Player toPlayer(PlayerJpaEntity playerEntity) {
        return new Player(new PlayerId(playerEntity.getId()), playerEntity.getName());
    }
}
