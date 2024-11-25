package be.kdg.prog6.storeContext.adapter.out;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog="store", name="game")
public class StoreGameJpaEntity {

    @Id
    @Column(name="game_id", columnDefinition = "CHAR(36)")
    private UUID gameId;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private BigDecimal price;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewJpaEntity> reviews;


    public StoreGameJpaEntity(UUID gameId, String name, BigDecimal price) {
        this.gameId = gameId;
        this.name = name;
        this.price = price;
    }

    public StoreGameJpaEntity() {}

    public UUID getGameId() {return gameId;}

    public void setGameId(UUID gameId) {this.gameId = gameId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public List<ReviewJpaEntity> getReviews() {return reviews;}

    public void setReviews(List<ReviewJpaEntity> reviews) {this.reviews = reviews;}

}
