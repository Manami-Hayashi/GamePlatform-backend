package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.ToggleFavoritePlayerUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.GetFriendsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    private final GetFriendsUseCase getFriendsUseCase;
    private final ToggleFavoritePlayerUseCase toggleFavoritePlayerUseCase;

    public FriendsController(GetFriendsUseCase getFriendsUseCase, ToggleFavoritePlayerUseCase toggleFavoritePlayerUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;
        this.toggleFavoritePlayerUseCase = toggleFavoritePlayerUseCase;
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
    public void toggleFavoriteGame(
            @PathVariable UUID playerId,
            @PathVariable UUID friendId) {
        toggleFavoritePlayerUseCase.toggleFavoritePlayer(new PlayerId(playerId), new PlayerId(friendId));
    }
}
