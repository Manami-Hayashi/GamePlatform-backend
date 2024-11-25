package be.kdg.prog6.gameManagementContext.ports.in;

import java.math.BigDecimal;

public record AddGameCommand(String gameName, BigDecimal price, String description) {
}