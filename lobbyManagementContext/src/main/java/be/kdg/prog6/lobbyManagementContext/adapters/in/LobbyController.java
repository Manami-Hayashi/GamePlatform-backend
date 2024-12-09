package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.ports.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private final MatchWithRandomPlayerUseCase matchWithRandomPlayerUseCase;
    private final MatchWithFriendUseCase matchWithFriendUseCase;
    private final GetGameIdByNameUseCase getGameIdByNameUseCase;
    private final GetFriendsUseCase getFriendsUseCase;
    private final GetLobbyIdUseCase getLobbyIdUseCase;

    @Autowired
    public LobbyController(MatchWithRandomPlayerUseCase matchWithRandomPlayerUseCase, MatchWithFriendUseCase matchWithFriendUseCase, GetGameIdByNameUseCase getGameIdByNameUseCase, GetFriendsUseCase getFriendsUseCase, GetLobbyIdUseCase getLobbyIdUseCase) {
        this.matchWithRandomPlayerUseCase = matchWithRandomPlayerUseCase;
        this.matchWithFriendUseCase = matchWithFriendUseCase;
        this.getGameIdByNameUseCase = getGameIdByNameUseCase;
        this.getFriendsUseCase = getFriendsUseCase;
        this.getLobbyIdUseCase = getLobbyIdUseCase;
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

    @PostMapping("/games/convert-name-to-id")
    public GameIdResponse convertGameNameToId(@RequestBody GameNameRequest request) {
        return getGameIdByNameUseCase.getGameIdByName(request.getGameName());
    }

    @GetMapping("/friends")
    public List<Player> getFriends() {
        return getFriendsUseCase.getFriends();
    }

    @GetMapping("/get-lobby-id/{playerId}")
    public UUID getLobbyId(@PathVariable UUID playerId) {
        return getLobbyIdUseCase.getLobbyId(playerId);
    }


}