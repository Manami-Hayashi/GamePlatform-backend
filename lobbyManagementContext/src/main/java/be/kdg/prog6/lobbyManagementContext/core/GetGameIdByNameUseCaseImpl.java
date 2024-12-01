package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.ports.in.GameIdResponse;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetGameIdByNameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyGameByNamePort;
import org.springframework.stereotype.Service;

@Service
public class GetGameIdByNameUseCaseImpl implements GetGameIdByNameUseCase {
    private final LoadLobbyGameByNamePort loadLobbyGamePort;

    public GetGameIdByNameUseCaseImpl(LoadLobbyGameByNamePort loadLobbyGamePort) {
        this.loadLobbyGamePort = loadLobbyGamePort;
    }

    @Override
    public GameIdResponse getGameIdByName(String gameName) {
        Game game = loadLobbyGamePort.loadLobbyGameByName(gameName);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return new GameIdResponse(game.getGameId().id().toString());
    }
}