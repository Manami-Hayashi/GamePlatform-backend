package be.kdg.prog6.gameManagementContext.adapters.out.db;

import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.ports.out.LoadGamePort;
import be.kdg.prog6.gameManagementContext.ports.out.LoadAllGamesPort;
import be.kdg.prog6.gameManagementContext.ports.out.SaveGamePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameDBAdapter implements SaveGamePort, LoadGamePort, LoadAllGamesPort {
    private final GameJpaRepository gameJpaRepository;

    public GameDBAdapter(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
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
                gameJpaEntity.getDescription()
        );
    }

    private GameJpaEntity toGameJpaEntity(Game game) {
        GameJpaEntity gameJpaEntity = new GameJpaEntity();
        gameJpaEntity.setGameId(game.getGameId().id());
        gameJpaEntity.setGameName(game.getGameName());
        gameJpaEntity.setDescription(game.getDescription());
        return gameJpaEntity;
    }
}