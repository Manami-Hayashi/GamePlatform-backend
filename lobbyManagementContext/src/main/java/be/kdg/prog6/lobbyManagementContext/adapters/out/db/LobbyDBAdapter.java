// LobbyDBAdapter.java
package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.*;
import be.kdg.prog6.lobbyManagementContext.ports.out.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Comparator;

@Component
public class LobbyDBAdapter implements SaveLobbyPort, LoadLobbyPort, LoadAllLobbiesPort, LoadPlayerPort, UpdatePlayerPort, LobbyGameCreatedPort, LoadLobbyGamePort, LobbyPlayerCreatedPort, LoadAllPlayersPort, LoadLobbyGameByNamePort, LoadLobbyByPlayerIdPort, CheckAllPlayersReadyPort, LobbyGamePurchasedPort, LoadLatestLobbyPort{
    private final LobbyJpaRepository lobbyJpaRepository;
    private final LobbyPlayerJpaRepository lobbyPlayerJpaRepository;
    private final LobbyGameJpaRepository lobbyGameJpaRepository;

    public LobbyDBAdapter(LobbyJpaRepository lobbyJpaRepository, LobbyPlayerJpaRepository lobbyPlayerJpaRepository, LobbyGameJpaRepository lobbyGameJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
        this.lobbyPlayerJpaRepository = lobbyPlayerJpaRepository;
        this.lobbyGameJpaRepository = lobbyGameJpaRepository;
    }

    @Override
    public void saveLobby(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = toLobbyJpaEntity(lobby);
        lobbyJpaRepository.save(lobbyJpaEntity);
        lobby.getPlayerIds().forEach(playerId -> {
            LobbyPlayerJpaEntity playerJpaEntity = lobbyPlayerJpaRepository.findById(playerId.id())
                    .map(existingPlayer -> {
                        existingPlayer.setLastActive(Instant.now());
                        return existingPlayer;
                    })
                    .orElseGet(() -> toLobbyPlayerJpaEntity(playerId, lobbyJpaEntity));
            lobbyPlayerJpaRepository.save(playerJpaEntity);
        });
    }

    @Override
    public void updatePlayer(Player player) {
        LobbyPlayerJpaEntity playerJpaEntity = lobbyPlayerJpaRepository.findById(player.getPlayerId().id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        playerJpaEntity.setLastActive(player.getLastActive());
        playerJpaEntity.setName(player.getName());
        playerJpaEntity.setReady(player.isReady());

        if (player.getLobbyId() != null) {
            LobbyJpaEntity lobbyJpaEntity = lobbyJpaRepository.findById(player.getLobbyId())
                    .orElseThrow(() -> new IllegalArgumentException("Lobby not found for player"));
            playerJpaEntity.setLobby(lobbyJpaEntity);
        } else {
            playerJpaEntity.setLobby(null);
        }

        if (player.getGameId() != null) {
            LobbyGameJpaEntity lobbyGameJpaEntity = lobbyGameJpaRepository.findById(player.getGameId().id())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found for the player"));
            playerJpaEntity.setGame(lobbyGameJpaEntity);
        } else {
            playerJpaEntity.setGame(null);
        }

        lobbyPlayerJpaRepository.save(playerJpaEntity);
    }

    @Override
    public Lobby loadLobby(UUID lobbyId) {
        return lobbyJpaRepository.findById(lobbyId)
                .map(this::toLobby)
                .orElse(null);
    }

    @Override
    public List<Lobby> loadAllLobbies() {
        return lobbyJpaRepository.findAll().stream()
                .map(this::toLobby)
                .collect(Collectors.toList());
    }

    @Override
    public Player loadPlayer(UUID playerId) {
        return lobbyPlayerJpaRepository.findById(playerId)
                .map(this::toPlayer)
                .orElse(null);
    }

    @Override
    public void createLobbyGame(Game game) {
        LobbyGameJpaEntity gameEntity = new LobbyGameJpaEntity(game.getGameId().id(), game.getGameName());
        lobbyGameJpaRepository.save(gameEntity);
    }

    @Override
    public Game loadLobbyGame(UUID gameId) {
        return lobbyGameJpaRepository.findById(gameId)
                .map(this::toGame)
                .orElse(null);
    }

    private Lobby toLobby(LobbyJpaEntity lobbyJpaEntity) {
        GameId gameId = (lobbyJpaEntity.getGame() != null) ? new GameId(lobbyJpaEntity.getGame().getGameId()) : null;
        return new Lobby(
                lobbyJpaEntity.getLobbyId(),
                lobbyJpaEntity.getPlayers().stream()
                        .map(this::toPlayerId)
                        .collect(Collectors.toList()),
                gameId
        );
    }

    private LobbyJpaEntity toLobbyJpaEntity(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = new LobbyJpaEntity();
        lobbyJpaEntity.setLobbyId(lobby.getLobbyId());
        lobbyJpaEntity.setCreationTime(Instant.now());
        lobbyJpaEntity.setPlayers(
                lobby.getPlayerIds().stream()
                        .map(playerId -> toLobbyPlayerJpaEntity(playerId, lobbyJpaEntity))
                        .collect(Collectors.toList())
        );
        lobbyJpaEntity.setGame(lobbyGameJpaRepository.findById(lobby.getGameId().id())
                .orElseThrow(() -> new IllegalArgumentException("Game not found")));
        return lobbyJpaEntity;
    }

    private LobbyPlayerJpaEntity toLobbyPlayerJpaEntity(PlayerId playerId, LobbyJpaEntity lobbyJpaEntity) {
        LobbyPlayerJpaEntity playerJpaEntity = new LobbyPlayerJpaEntity();
        playerJpaEntity.setPlayerId(playerId.id());
        playerJpaEntity.setLobby(lobbyJpaEntity);
        playerJpaEntity.setLastActive(Instant.now());
        playerJpaEntity.setReady(false); // Default value

        // Set the game for the player
        if (lobbyJpaEntity.getGame() != null) {
            playerJpaEntity.setGame(lobbyJpaEntity.getGame());
        } else {
            Player player = loadPlayer(playerId.id());
            if (player != null && player.getGameId() != null) {
                LobbyGameJpaEntity gameEntity = lobbyGameJpaRepository.findById(player.getGameId().id())
                        .orElseThrow(() -> new IllegalArgumentException("Game not found for the player"));
                playerJpaEntity.setGame(gameEntity);
            } else {
                playerJpaEntity.setGame(null);
            }
        }

        return playerJpaEntity;
    }

    private PlayerId toPlayerId(LobbyPlayerJpaEntity playerJpaEntity) {
        return new PlayerId(playerJpaEntity.getPlayerId());
    }

    private Player toPlayer(LobbyPlayerJpaEntity playerJpaEntity) {
        LobbyJpaEntity lobby = playerJpaEntity.getLobby();
        UUID lobbyId = (lobby != null) ? lobby.getLobbyId() : null;
        GameId gameId = (playerJpaEntity.getGame() != null) ? new GameId(playerJpaEntity.getGame().getGameId()) : null;

        return new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getName(),
                playerJpaEntity.getLastActive(),
                lobbyId,
                gameId,
                playerJpaEntity.getReady() != null ? playerJpaEntity.getReady() : false // Handle null value
        );
    }

    private Game toGame(LobbyGameJpaEntity gameEntity) {
        return new Game(new GameId(gameEntity.getGameId()), gameEntity.getName());
    }

    @Override
    public void createPlayer(Player player) {
        LobbyPlayerJpaEntity jpaEntity = new LobbyPlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );
        // save to database
        lobbyPlayerJpaRepository.save(jpaEntity);
    }

    @Override
    public List<Player> loadAllPlayers() {
        return lobbyPlayerJpaRepository.findAll().stream()
                .map(this::toPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public Game loadLobbyGameByName(String gameName) {
        return lobbyGameJpaRepository.findByName(gameName)
                .map(this::toGame)
                .orElse(null);
    }

    @Override
    public Lobby loadLobbyByPlayerId(UUID playerId) {
        return lobbyPlayerJpaRepository.findById(playerId)
                .map(LobbyPlayerJpaEntity::getLobby)
                .map(this::toLobby)
                .orElse(null);
    }

    @Override
    public boolean areAllPlayersReady(UUID lobbyId) {
        LobbyJpaEntity lobbyJpaEntity = lobbyJpaRepository.findById(lobbyId)
                .orElseThrow(() -> new IllegalArgumentException("Lobby not found"));
        return lobbyJpaEntity.getPlayers().stream()
                .filter(player -> player.getLobby().getLobbyId().equals(lobbyId))
                .allMatch(LobbyPlayerJpaEntity::getReady);
    }

    @Override
    public void purchaseLobbyGame(PlayerId playerId, Game game) {
        LobbyPlayerJpaEntity playerJpaEntity = lobbyPlayerJpaRepository.findById(playerId.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        LobbyGameJpaEntity gameJpaEntity = new LobbyGameJpaEntity();
        gameJpaEntity.setGameId(game.getGameId().id());
        gameJpaEntity.setName(game.getGameName());
        gameJpaEntity.setPlayer(playerJpaEntity);

        lobbyGameJpaRepository.save(gameJpaEntity);
    }

    @Override
    public Lobby loadLatestLobby() {
        return lobbyJpaRepository.findAll().stream()
                .max(Comparator.comparing(LobbyJpaEntity::getCreationTime))
                .map(this::toLobby)
                .orElse(null);
    }
}