package be.kdg.prog6.lobbyManagementContext.ports.in;

public interface GetGameIdByNameUseCase {
    GameIdResponse getGameIdByName(String gameName);
}