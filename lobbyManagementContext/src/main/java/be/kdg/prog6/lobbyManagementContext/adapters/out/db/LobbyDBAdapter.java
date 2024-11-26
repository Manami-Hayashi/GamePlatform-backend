package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadAllLobbiesPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LobbyDBAdapter implements SaveLobbyPort, LoadLobbyPort, LoadAllLobbiesPort {
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
        lobby.getPlayers().forEach(player -> {
            LobbyPlayerJpaEntity playerJpaEntity = toLobbyPlayerJpaEntity(player, lobbyJpaEntity);
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

    private Lobby toLobby(LobbyJpaEntity lobbyJpaEntity) {
        return new Lobby(
                lobbyJpaEntity.getLobbyId(),
                lobbyJpaEntity.getPlayers().stream()
                        .map(this::toPlayer)
                        .collect(Collectors.toList())
        );
    }

    private LobbyJpaEntity toLobbyJpaEntity(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = new LobbyJpaEntity();
        lobbyJpaEntity.setLobbyId(lobby.getLobbyId());
        lobbyJpaEntity.setPlayers(
                lobby.getPlayers().stream()
                        .map(player -> toLobbyPlayerJpaEntity(player, lobbyJpaEntity))
                        .collect(Collectors.toList())
        );
        return lobbyJpaEntity;
    }

    private LobbyPlayerJpaEntity toLobbyPlayerJpaEntity(Player player, LobbyJpaEntity lobbyJpaEntity) {
        LobbyPlayerJpaEntity playerJpaEntity = new LobbyPlayerJpaEntity();
        playerJpaEntity.setPlayerId(player.getPlayerId().id());
        playerJpaEntity.setName(player.getName());
        playerJpaEntity.setLobby(lobbyJpaEntity);
        return playerJpaEntity;
    }

    private Player toPlayer(LobbyPlayerJpaEntity playerJpaEntity) {
        return new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getName()
        );
    }
}