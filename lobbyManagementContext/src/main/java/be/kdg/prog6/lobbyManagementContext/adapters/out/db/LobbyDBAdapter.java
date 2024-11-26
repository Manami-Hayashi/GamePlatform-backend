package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LobbyDBAdapter implements SaveLobbyPort, LoadLobbyPort, LoadAllLobbiesPort, LoadPlayerPort {
    private final LobbyJpaRepository lobbyJpaRepository;
    private final LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    public LobbyDBAdapter(LobbyJpaRepository lobbyJpaRepository, LobbyPlayerJpaRepository lobbyPlayerJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
        this.lobbyPlayerJpaRepository = lobbyPlayerJpaRepository;
    }

    @Override
    public void saveLobby(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = toLobbyJpaEntity(lobby);
        lobbyJpaRepository.save(lobbyJpaEntity);
        lobby.getPlayerIds().forEach(playerId -> {
            LobbyPlayerJpaEntity playerJpaEntity = toLobbyPlayerJpaEntity(playerId, lobbyJpaEntity);
            lobbyPlayerJpaRepository.save(playerJpaEntity);
        });
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

    private Lobby toLobby(LobbyJpaEntity lobbyJpaEntity) {
        // Use the constructor that takes a list of PlayerIds
        return new Lobby(
                lobbyJpaEntity.getLobbyId(),
                lobbyJpaEntity.getPlayers().stream()
                        .map(this::toPlayerId)
                        .collect(Collectors.toList())
        );
    }

    private LobbyJpaEntity toLobbyJpaEntity(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = new LobbyJpaEntity();
        lobbyJpaEntity.setLobbyId(lobby.getLobbyId());
        lobbyJpaEntity.setPlayers(
                lobby.getPlayerIds().stream()
                        .map(playerId -> toLobbyPlayerJpaEntity(playerId, lobbyJpaEntity))
                        .collect(Collectors.toList())
        );
        return lobbyJpaEntity;
    }

    private LobbyPlayerJpaEntity toLobbyPlayerJpaEntity(PlayerId playerId, LobbyJpaEntity lobbyJpaEntity) {
        LobbyPlayerJpaEntity playerJpaEntity = new LobbyPlayerJpaEntity();
        playerJpaEntity.setPlayerId(playerId.id());
        playerJpaEntity.setLobby(lobbyJpaEntity);
        return playerJpaEntity;
    }

    private PlayerId toPlayerId(LobbyPlayerJpaEntity playerJpaEntity) {
        return new PlayerId(playerJpaEntity.getPlayerId());
    }

    private Player toPlayer(LobbyPlayerJpaEntity playerJpaEntity) {
        return new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getName(),
                playerJpaEntity.getLastActive()
        );
    }
}
