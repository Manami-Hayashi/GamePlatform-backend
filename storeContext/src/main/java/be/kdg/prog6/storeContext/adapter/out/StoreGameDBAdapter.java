package be.kdg.prog6.storeContext.adapter.out;

import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.PlayerId;
import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
import be.kdg.prog6.storeContext.port.out.StoreGameCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreGameDBAdapter implements LoadStoreGamePort, StoreGameCreatedPort {
    private final StoreGameJpaRepository storeGameJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(StoreGameDBAdapter.class);

    public StoreGameDBAdapter(StoreGameJpaRepository storeGameJpaRepository) {
        this.storeGameJpaRepository = storeGameJpaRepository;
    }

    @Override
    public List<StoreGame> findAll() {
        List<StoreGameJpaEntity> storeGameJpaEntities = storeGameJpaRepository.findAll();
        if (storeGameJpaEntities.isEmpty()) {
            logger.info("No games found in the store");
            return List.of();
        }

        List<StoreGame> storeGameList = storeGameJpaEntities
                .stream()
                .map(this::toDomain)
                .toList();

        logger.info("Getting all games from the store");
        return storeGameList;

    }

    private StoreGame toDomain(StoreGameJpaEntity storeGameJpaEntity) {
        StoreGame game = new StoreGame();
        game.setGameId(new GameId(storeGameJpaEntity.getGameId()));
        game.setName(storeGameJpaEntity.getName());
        game.setPrice(storeGameJpaEntity.getPrice());
        game.setDescription(storeGameJpaEntity.getDescription());
        if (!storeGameJpaEntity.getReviews().isEmpty()) {
            List<Review> reviewList = storeGameJpaEntity.getReviews()
                    .stream()
                    .map(reviewJpaEntity -> new Review(
                            reviewJpaEntity.getReviewId(),
                            new PlayerId(reviewJpaEntity.getPlayerId()),
                            new GameId(reviewJpaEntity.getGameId()),
                            reviewJpaEntity.getRating(),
                            reviewJpaEntity.getComment(),
                            reviewJpaEntity.getCreatedAt()
                    ))
                    .toList();
            game.setReviews(reviewList);
            return game;
        }
        return game;
    }

    @Override
    public void createStoreGame(StoreGame newStoreGame) {

        StoreGameJpaEntity jpaEntity= new StoreGameJpaEntity(
                newStoreGame.getGameId().id(),
                newStoreGame.getName(),
                newStoreGame.getPrice(),
                newStoreGame.getDescription()
        );
        if (!newStoreGame.getReviews().isEmpty()) {
            jpaEntity.setReviews(newStoreGame.getReviews().stream()
                    .map(review -> new ReviewJpaEntity(
                            review.getReviewId(),
                            review.getPlayerId().id(),
                            jpaEntity,
                            review.getRating(),
                            review.getComment(),
                            review.getCreatedAt()
                    ))
                    .collect(Collectors.toList())
            );

            storeGameJpaRepository.save(jpaEntity);
        }
        else {
            storeGameJpaRepository.save(jpaEntity);
        }

    }
}
