package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
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

    public LobbyDBAdapter(LobbyJpaRepository lobbyJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
    }

    @Override
    public void saveLobby(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = toLobbyJpaEntity(lobby);
        lobbyJpaRepository.save(lobbyJpaEntity);
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
                        .map(player -> new PlayerId(player.getPlayerId()))
                        .collect(Collectors.toList())
        );
    }

    private LobbyJpaEntity toLobbyJpaEntity(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = new LobbyJpaEntity();
        lobbyJpaEntity.setLobbyId(lobby.getLobbyId());
        lobbyJpaEntity.setPlayers(
                lobby.getPlayers().stream()
                        .map(playerId -> {
                            LobbyPlayerJpaEntity playerJpaEntity = new LobbyPlayerJpaEntity();
                            playerJpaEntity.setPlayerId(playerId.id());
                            return playerJpaEntity;
                        })
                        .collect(Collectors.toList())
        );
        return lobbyJpaEntity;
    }
}