// GameController.java
package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.CheckReadyStatusUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.StartGameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyByPlayerIdPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class GameController {

    private final StartGameUseCase startGameUseCase;
    private final CheckReadyStatusUseCase checkReadyStatusUseCase;

    @Autowired
    public GameController(StartGameUseCase startGameUseCase, CheckReadyStatusUseCase checkReadyStatusUseCase) {
        this.startGameUseCase = startGameUseCase;
        this.checkReadyStatusUseCase = checkReadyStatusUseCase;
    }

    @PostMapping("/start-session")
    public ResponseEntity<GameSession> startSession(@RequestBody PlayerId playerId) {
        GameSession gameSession = startGameUseCase.readyUp(playerId);
        if (gameSession != null && gameSession.isActive()) {
            return ResponseEntity.ok(gameSession);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/ready-status/{playerId}")
    public ResponseEntity<Boolean> checkReadyStatus(@PathVariable UUID playerId) {
        boolean allPlayersReady = checkReadyStatusUseCase.checkReadyStatus(playerId);
        return ResponseEntity.ok(allPlayersReady);
    }
}