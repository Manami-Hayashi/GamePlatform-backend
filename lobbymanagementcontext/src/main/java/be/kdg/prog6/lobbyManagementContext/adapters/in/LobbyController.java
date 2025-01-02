package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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



    @GetMapping("/friends/{playerId}")
    public ResponseEntity<List<PlayerDto>> getFriends(@PathVariable String playerId) {
        List<Player> friends = getFriendsUseCase.getFriends(UUID.fromString(playerId));
        List<PlayerDto> friendDtos = friends.stream()
                .map(friend -> new PlayerDto(friend.getPlayerId().id().toString(), friend.getName()))
                .toList();
        return ResponseEntity.ok(friendDtos);
    }

    @GetMapping("/get-lobby-id/{playerId}")
    public UUID getLobbyId(@PathVariable UUID playerId) {
        return getLobbyIdUseCase.getLobbyId(playerId);
    }

}
