package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithFriendUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithRandomPlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private final MatchWithRandomPlayerUseCase matchWithRandomPlayerUseCase;
    private final MatchWithFriendUseCase matchWithFriendUseCase;

    @Autowired
    public LobbyController(MatchWithRandomPlayerUseCase matchWithRandomPlayerUseCase, MatchWithFriendUseCase matchWithFriendUseCase) {
        this.matchWithRandomPlayerUseCase = matchWithRandomPlayerUseCase;
        this.matchWithFriendUseCase = matchWithFriendUseCase;
    }

    @PostMapping("/match/random")
    public void matchWithRandomPlayer(@RequestBody MatchPlayersRequest request) {
        MatchPlayersCommand command = new MatchPlayersCommand(request.getPlayerId(), request.getGameId());
        matchWithRandomPlayerUseCase.matchPlayers(command);
    }

    @PostMapping("/match/friend")
    public void matchPlayersWithFriend(@RequestBody MatchPlayersRequest request) {
        MatchPlayersCommand command = new MatchPlayersCommand(request.getPlayerId(), request.getFriendId(), request.getGameId());
        matchWithFriendUseCase.matchPlayers(command);
    }
}