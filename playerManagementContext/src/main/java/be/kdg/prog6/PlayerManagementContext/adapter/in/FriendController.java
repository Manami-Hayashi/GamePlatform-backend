package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.AddFriendUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.ToggleFavoritePlayerUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.GetFriendsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final GetFriendsUseCase getFriendsUseCase;
    private final ToggleFavoritePlayerUseCase toggleFavoritePlayerUseCase;
    private final AddFriendUseCase addFriendUseCase;

    public FriendController(GetFriendsUseCase getFriendsUseCase, ToggleFavoritePlayerUseCase toggleFavoritePlayerUseCase, AddFriendUseCase addFriendUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;
        this.toggleFavoritePlayerUseCase = toggleFavoritePlayerUseCase;
        this.addFriendUseCase = addFriendUseCase;
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<List<FriendDto>> getFriends(@PathVariable UUID playerId) {
        List<Friend> friends = getFriendsUseCase.getFriends(new PlayerId(playerId));
        List<FriendDto> friendDtos = friends.stream()
                .map(friend -> new FriendDto(friend.getFriendId().id().toString(), friend.getName(), friend.isFavorite()))
                .toList();
        return ResponseEntity.ok(friendDtos);
    }

    @PostMapping("/{playerId}/toggle-favorite/{friendId}")
    public void toggleFavoritePlayer (
            @PathVariable UUID playerId,
            @PathVariable UUID friendId) {
        toggleFavoritePlayerUseCase.toggleFavoritePlayer(new PlayerId(playerId), new PlayerId(friendId));
    }

    @PostMapping("/{playerId}/add-friend/{friendId}")
    public void addFriend (
            @PathVariable UUID playerId,
            @PathVariable UUID friendId) {
        addFriendUseCase.addFriend(new PlayerId(playerId), new PlayerId(friendId));
    }
}
