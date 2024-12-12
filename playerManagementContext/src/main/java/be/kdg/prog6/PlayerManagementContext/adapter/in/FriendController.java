package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AcceptFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.SendFriendRequestUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.ShowFriendsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final ShowFriendsUseCase showFriendsUseCase;
    private final SendFriendRequestUseCase sendFriendRequestUseCase;
    private final AcceptFriendRequestUseCase acceptFriendRequestUseCase;

    public FriendController(ShowFriendsUseCase showFriendsUseCase, SendFriendRequestUseCase sendFriendRequestUseCase, AcceptFriendRequestUseCase acceptFriendRequestUseCase) {
        this.showFriendsUseCase = showFriendsUseCase;
        this.sendFriendRequestUseCase = sendFriendRequestUseCase;
        this.acceptFriendRequestUseCase = acceptFriendRequestUseCase;
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<List<FriendDto>> getFriends(@PathVariable String playerId) {
        List<Friend> friends = showFriendsUseCase.getFriends(new PlayerId(UUID.fromString(playerId)));
        List<FriendDto> friendDtos = friends.stream()
                .map(friend -> new FriendDto(friend.getPlayer1().getPlayerId().id().toString(), friend.getPlayer2().getName(), friend.getFriendRequestStatus().toString()))
                .toList();
        return ResponseEntity.ok(friendDtos);
    }

    @PostMapping("/{playerId}/send-request/{friendId}")
    public void sendFriendRequest(
            @PathVariable String playerId,
            @PathVariable String friendId) {
        sendFriendRequestUseCase.sendFriendRequest(new PlayerId(UUID.fromString(playerId)), new PlayerId(UUID.fromString(friendId)));
    }

    @PostMapping("/{playerId}/accept-request/{friendId}")
    public void acceptFriendRequest(
            @PathVariable String playerId,
            @PathVariable String friendId) {
        acceptFriendRequestUseCase.acceptFriendRequest(new PlayerId(UUID.fromString(playerId)), new PlayerId(UUID.fromString(friendId)));
    }
}
