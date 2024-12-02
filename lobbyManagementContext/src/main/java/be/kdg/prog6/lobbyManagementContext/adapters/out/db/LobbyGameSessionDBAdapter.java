package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveGameSessionPort;
import org.springframework.stereotype.Component;

@Component
public class LobbyGameSessionDBAdapter implements SaveGameSessionPort {
    private final LobbyGameSessionJpaRepository LobbygameSessionJpaRepository;

    public LobbyGameSessionDBAdapter(LobbyGameSessionJpaRepository lobbygameSessionJpaRepository) {
        LobbygameSessionJpaRepository = lobbygameSessionJpaRepository;

    }

    @Override
    public void saveGameSession(GameSession gameSession) {
        LobbyGameSessionJpaEntity entity = toJpaEntity(gameSession);
        LobbygameSessionJpaRepository.save(entity);
    }

    private LobbyGameSessionJpaEntity toJpaEntity(GameSession gameSession) {
        LobbyGameSessionJpaEntity entity = new LobbyGameSessionJpaEntity();
        entity.setSessionId(gameSession.getSessionId());
        entity.setGameId(gameSession.getGameId().id());
        entity.setPlayerIds(gameSession.getPlayerIds().stream().map(PlayerId::id).toList());
        entity.setStartTime(gameSession.getStartTime());
        entity.setEndTime(gameSession.getEndTime());
        entity.setActive(gameSession.isActive());
        entity.setWinner(gameSession.getWinner() != null ? gameSession.getWinner().id() : null);
        return entity;
    }
}