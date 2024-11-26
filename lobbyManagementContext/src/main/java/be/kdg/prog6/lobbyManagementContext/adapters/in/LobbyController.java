package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private final MatchPlayersUseCase matchPlayersUseCase;

    @Autowired
    public LobbyController(MatchPlayersUseCase matchPlayersUseCase) {
        this.matchPlayersUseCase = matchPlayersUseCase;
    }

    @PostMapping("/match")
    public void matchPlayers(@RequestBody MatchPlayersRequest request) {
        MatchPlayersCommand command = new MatchPlayersCommand(request.getPlayerId(), request.getFriendId());
        matchPlayersUseCase.matchPlayers(command);
    }
}