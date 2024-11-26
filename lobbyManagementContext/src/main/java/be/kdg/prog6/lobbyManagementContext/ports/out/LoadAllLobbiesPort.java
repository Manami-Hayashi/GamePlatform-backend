package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import java.util.List;

@FunctionalInterface
public interface LoadAllLobbiesPort {
    List<Lobby> loadAllLobbies();
}