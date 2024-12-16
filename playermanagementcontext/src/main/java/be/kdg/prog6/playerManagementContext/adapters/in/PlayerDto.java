package be.kdg.prog6.playerManagementContext.adapters.in;

public class PlayerDto {
    private final String playerId;
    private final String name;

    public PlayerDto(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
