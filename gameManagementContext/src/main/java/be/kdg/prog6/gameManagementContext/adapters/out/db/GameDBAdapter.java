package be.kdg.prog6.gameManagementContext.adapters.out.db;

import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.domain.Player;
import be.kdg.prog6.gameManagementContext.ports.out.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameDBAdapter implements SaveGamePort, LoadGamePort, LoadAllGamesPort, UpdateGamePort, GameMngPlayerCreatedPort {
    private final GameJpaRepository gameJpaRepository;
    private final GameMngPlayerJpaRepository gameMngPlayerJpaRepository;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(GameDBAdapter.class);

    public GameDBAdapter(GameJpaRepository gameJpaRepository, GameMngPlayerJpaRepository gameMngPlayerJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
        this.gameMngPlayerJpaRepository = gameMngPlayerJpaRepository;
    }

    @Override
    public void saveGame(Game game) {
        GameJpaEntity gameJpaEntity = toGameJpaEntity(game);
        gameJpaRepository.save(gameJpaEntity);
    }

    @Override
    public Game loadGame(String gameName) {
        return gameJpaRepository.findByGameName(gameName)
                .map(this::toGame)
                .orElse(null);
    }

    @Override
    public List<Game> loadAllGames() {
        return gameJpaRepository.findAll().stream()
                .map(this::toGame)
                .collect(Collectors.toList());
    }

    private Game toGame(GameJpaEntity gameJpaEntity) {
        return new Game(
                new GameId(gameJpaEntity.getGameId()),
                gameJpaEntity.getGameName(),
                gameJpaEntity.getPrice(),
                gameJpaEntity.getDescription()
        );
    }

    private GameJpaEntity toGameJpaEntity(Game game) {
        GameJpaEntity gameJpaEntity = new GameJpaEntity();
        gameJpaEntity.setGameId(game.getGameId().id());
        gameJpaEntity.setGameName(game.getGameName());
        gameJpaEntity.setPrice(game.getPrice());
        gameJpaEntity.setDescription(game.getDescription());
        return gameJpaEntity;
    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void createPlayer(Player player) {
        GameMngPlayerJpaEntity gameMngPlayerJpaEntity = new GameMngPlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName(),
                player.getAge(),
                player.getGender(),
                player.getLocation()
        );
        logger.info("creating new Player with name {}", player.getName());
        gameMngPlayerJpaRepository.save(gameMngPlayerJpaEntity);
    }
}