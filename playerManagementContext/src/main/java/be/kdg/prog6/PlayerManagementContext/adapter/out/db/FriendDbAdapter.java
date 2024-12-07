package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.out.CreateFriendPort;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsOfPlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdateFriendPort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendDbAdapter implements LoadFriendsPort, LoadFriendsOfPlayerPort, UpdateFriendPort, CreateFriendPort {
    private final FriendJpaRepository friendJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;

    public FriendDbAdapter(FriendJpaRepository friendJpaRepository, PlayerJpaRepository playerJpaRepository) {
        this.friendJpaRepository = friendJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public List<Friend> loadFriends() {
        List<FriendJpaEntity> friendJpaEntities = friendJpaRepository.findAll();
        return toFriends(friendJpaEntities);
    }

    @Override
    public List<Friend> loadFriendsOfPlayer(UUID playerId) {
        List<FriendJpaEntity> friendJpaEntities = friendJpaRepository.findByPlayerId(playerId);
        return toFriends(friendJpaEntities);
    }

    @Override
    public void updateFriend(Friend friend, UUID playerId) {
        Optional<PlayerJpaEntity> optionalPlayerJpaEntity = playerJpaRepository.findByPlayerId(playerId);
        if (optionalPlayerJpaEntity.isEmpty()) {
            throw new IllegalArgumentException("Player not found");
        }
        PlayerJpaEntity playerJpaEntity = optionalPlayerJpaEntity.get();
        FriendJpaEntity friendJpaEntity = friendJpaRepository.findByPlayerIdAndPlayer(friend.getFriendId().id(), playerJpaEntity);
        friendJpaEntity.setFavorite(friend.isFavorite());
        friendJpaRepository.save(friendJpaEntity);
    }

    @Override
    public void createFriend(Friend friend, Player player) {
        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity(player.getPlayerId().id(), player.getName());
        FriendJpaEntity friendJpaEntity = new FriendJpaEntity(
                friend.getFriendId().id(),
                friend.getName(),
                friend.isFavorite(),
                friend.getFriendRequestStatus().toString(),
                playerJpaEntity
        );
        friendJpaRepository.save(friendJpaEntity);
    }

    private List<Friend> toFriends(List<FriendJpaEntity> friendJpaEntities) {
        List<Friend> friends = new ArrayList<>();
        for (FriendJpaEntity friendJpaEntity : friendJpaEntities) {
            Player player = new Player(new PlayerId(friendJpaEntity.getPlayer().getPlayerId()), friendJpaEntity.getPlayer().getName());
            Friend friend = new Friend(
                    new PlayerId(friendJpaEntity.getPlayerId()),
                    friendJpaEntity.getName(),
                    friendJpaEntity.isFavorite(),
                    FriendRequestStatus.valueOf(friendJpaEntity.getFriendRequestStatus()),
                    player
            );
            friends.add(friend);
        }
        return friends;
    }
}
