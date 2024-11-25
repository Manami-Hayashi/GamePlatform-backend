package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public record MatchPlayersCommand(UUID playerId, UUID friendId) {
}