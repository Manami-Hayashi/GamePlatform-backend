package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.GameId;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.out.GameCreatedPort;
import be.kdg.prog6.playerManagementContext.ports.out.GameLoadedPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component("playerGameDBAdapter")
public class GameOwnedDBAdapter implements GameLoadedPort, GameCreatedPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameOwnedDBAdapter.class);
    private final GameOwnedJpaRepository gameOwnedJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private final RestTemplate restTemplate;

    public GameOwnedDBAdapter(GameOwnedJpaRepository gameOwnedJpaRepository, PlayerJpaRepository playerJpaRepository, RestTemplate restTemplate) {
        this.gameOwnedJpaRepository = gameOwnedJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void gameCreated(PlayerId playerId, Game game) {
        GameOwnedJpaEntity gameOwnedJpaEntity = new GameOwnedJpaEntity(
                game.getGameId().id(),
                game.getGameName(),
                game.isFavorite()
        );

        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByIdWithGameOwned(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        gameOwnedJpaEntity.setPlayer(playerJpaEntity);

        LOGGER.info("creating new Game with name {}", game.getGameName());

        gameOwnedJpaRepository.save(gameOwnedJpaEntity);

        if (game.getGameId().toString().equals("6bf497bd-b0a5-4421-a0af-c2d151bddf1f")) {
            GameOwnedJpaEntity checkersGame = new GameOwnedJpaEntity(
                    UUID.fromString("6bf497bd-b0a5-4421-a0af-c2d151bddf1f"),
                    "Hello World Clicker",
                    false,
                    playerJpaEntity
            );
            gameOwnedJpaRepository.save(checkersGame);
            String url = "http://localhost:8081/data/send-achievements";
            Map<String, Object> request = new HashMap<>();
            request.put("playerId", playerId.toString());
            restTemplate.postForObject(url, request, String.class);
        }
    }


    @Override
    @Transactional
    public List<Game> loadGames(PlayerId playerId) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(playerId.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        List<Game> gamesOwned = playerJpaEntity.getGameOwned().stream()
                .map(this::toDomain)
                .collect(toList());

        return gamesOwned;
    }

    private Game toDomain(GameOwnedJpaEntity jpaEntity) {
        return new Game (
                new GameId(jpaEntity.getGameId()),
                jpaEntity.getGameName(),
                jpaEntity.isFavorite()
        );
    }
}
