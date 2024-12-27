package be.kdg.prog6.gameStatisticsContext.port.out;

import java.util.UUID;

@FunctionalInterface
public interface UpdateMatchSessionPort {
        void updateMatchSession(UUID matchSessionId);
}
