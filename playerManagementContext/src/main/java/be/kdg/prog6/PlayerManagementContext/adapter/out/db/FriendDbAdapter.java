package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.FriendRequestStatus;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.out.CreateFriendPort;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsOfPlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdateFriendPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendDbAdapter implements LoadFriendsPort, LoadFriendsOfPlayerPort, UpdateFriendPort, CreateFriendPort {
    private final FriendJpaRepository friendJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;

    public FriendDbAdapter(FriendJpaRepository friendJpaRepository, PlayerJpaRepository playerJpaRepository) {
        this.friendJpaRepository = friendJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
    }

    @Transactional
    @Override
    public List<Friend> loadFriends() {
        if (friendJpaRepository.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        List<FriendJpaEntity> friendJpaEntities = friendJpaRepository.findAll();
        return toFriends(friendJpaEntities);
    }

    @Transactional
    @Override
    public List<Friend> loadFriendsOfPlayer(UUID playerId) {
        List<FriendJpaEntity> friendJpaEntities = friendJpaRepository.findByPlayerId(playerId);
        return toFriends(friendJpaEntities);
    }

    @Transactional
    @Override
    public void updateFriend(Friend friend, UUID playerId) {
        Optional<PlayerJpaEntity> optionalPlayerJpaEntity = playerJpaRepository.findByPlayerId(playerId);
        if (optionalPlayerJpaEntity.isEmpty()) {
            throw new IllegalArgumentException("Player not found");
        }
        PlayerJpaEntity playerJpaEntity = optionalPlayerJpaEntity.get();
        FriendJpaEntity friendJpaEntity = friendJpaRepository.findByPlayerIdAndPlayers(friend.getFriendId().id(), List.of(playerJpaEntity));
        friendJpaEntity.setFavorite(friend.isFavorite());
        friendJpaRepository.save(friendJpaEntity);
    }

    @Transactional
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
        if (friendJpaEntity.getPlayerId() != null && friendJpaEntity.getName() != null && friendJpaEntity.getFriendRequestStatus() != null && friendJpaEntity.getPlayer() != null) {
            try {
                FriendJpaEntity currentFriend1 = friendJpaRepository.findByPlayerId(friend.getFriendId().id()).get(0);
                FriendJpaEntity currentFriend2 = friendJpaRepository.findByPlayerId(friend.getFriendId().id()).get(0);
                assert currentFriend1 != null;
                if ((friendJpaEntity.getPlayerId() != currentFriend1.getPlayerId()) && !Objects.equals(friendJpaEntity.getFriendRequestStatus(), currentFriend1.getFriendRequestStatus())) {
                    friendJpaRepository.save(friendJpaEntity);
                }
                assert currentFriend2 != null;
                if ((friendJpaEntity.getPlayerId() != currentFriend2.getPlayerId()) && !Objects.equals(friendJpaEntity.getFriendRequestStatus(), currentFriend2.getFriendRequestStatus())) {
                    friendJpaRepository.save(friendJpaEntity);
                }
            } catch (Exception e) {
                friendJpaRepository.save(friendJpaEntity);
            }
        }
    }

    private List<Friend> toFriends(List<FriendJpaEntity> friendJpaEntities) {
        List<Friend> friends = new ArrayList<>();
        for (FriendJpaEntity friendJpaEntity : friendJpaEntities) {
            try {
                Player player = new Player(new PlayerId(friendJpaEntity.getPlayer().getPlayerId()), friendJpaEntity.getPlayer().getName());
                Friend friend = new Friend(
                        new PlayerId(friendJpaEntity.getPlayerId()),
                        friendJpaEntity.getName(),
                        friendJpaEntity.isFavorite(),
                        FriendRequestStatus.valueOf(friendJpaEntity.getFriendRequestStatus()),
                        player
                );
                friends.add(friend);
            } catch (IndexOutOfBoundsException e) {
                return new ArrayList<>();
            }
        }
        return friends;
    }
}
