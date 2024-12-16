package be.kdg.prog6.gameManagementContext.ports.out;

@FunctionalInterface
public interface GameMngPlayerCreatedPort {
    void createPlayer(be.kdg.prog6.gameManagementContext.domain.Player player);

}
