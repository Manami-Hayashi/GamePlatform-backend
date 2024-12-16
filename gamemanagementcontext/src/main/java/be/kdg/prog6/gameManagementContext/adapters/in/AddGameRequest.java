package be.kdg.prog6.gameManagementContext.adapters.in;

import java.math.BigDecimal;

public class AddGameRequest {
    private String gameName;
    private BigDecimal price = BigDecimal.ZERO; // Default price
    private String description;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}