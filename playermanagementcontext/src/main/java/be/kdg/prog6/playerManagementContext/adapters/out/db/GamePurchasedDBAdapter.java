package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.out.GamePurchasedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GamePurchasedDBAdapter implements GamePurchasedPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GamePurchasedDBAdapter.class);
    private final GameOwnedJpaRepository gameOwnedJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private final RestTemplate restTemplate;

    public GamePurchasedDBAdapter(GameOwnedJpaRepository gameOwnedJpaRepository, PlayerJpaRepository playerJpaRepository, RestTemplate restTemplate) {
        this.gameOwnedJpaRepository = gameOwnedJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void gamePurchased(PlayerId playerId, Game game) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(playerId.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        GameOwnedJpaEntity gameOwnedJpaEntity = new GameOwnedJpaEntity();
        gameOwnedJpaEntity.setGameId(game.getGameId().id());
        gameOwnedJpaEntity.setGameName(game.getGameName());
        gameOwnedJpaEntity.setPlayer(playerJpaEntity);

        LOGGER.info("Saving purchased game with ID {} for player with ID {}", game, playerId);

        gameOwnedJpaRepository.save(gameOwnedJpaEntity);

        if (game.getGameId().id().toString().equals("6bf497bd-b0a5-4421-a0af-c2d151bddf1f")) {
            String url = "http://localhost:8081/data/send-achievements";
            Map<String, Object> request = new HashMap<>();
            request.put("playerId", playerId.id().toString());
            restTemplate.postForObject(url, request, String.class);
            LOGGER.info("Player {} has been assigned the game '{}' in their library.", playerJpaEntity.getName(), game.getGameName());
        }
    }
}
