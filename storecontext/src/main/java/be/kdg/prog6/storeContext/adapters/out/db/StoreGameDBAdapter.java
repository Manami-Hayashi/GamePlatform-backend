package be.kdg.prog6.storeContext.adapters.out.db;

import be.kdg.prog6.storeContext.domain.CustomerId;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.out.LoadStoreGamePort;
import be.kdg.prog6.storeContext.port.out.StoreGameCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    @Override
    public StoreGame findById(GameId gameId) {
        logger.info("Finding game by ID: {}", gameId);
        Optional<StoreGameJpaEntity> storeGameJpaEntity = storeGameJpaRepository.findByIdWithReviews(gameId.id());
        if (storeGameJpaEntity.isPresent()) {
            logger.info("Game found: {}", storeGameJpaEntity.get().getName());
            return toDomain(storeGameJpaEntity.get());
        } else {
            logger.warn("Game not found for ID: {}", gameId);
            return null;
        }
    }

    private StoreGame toDomain(StoreGameJpaEntity storeGameJpaEntity) {
        StoreGame game = new StoreGame();
        game.setGameId(new GameId(storeGameJpaEntity.getGameId()));
        game.setGameName(storeGameJpaEntity.getName());
        game.setPrice(storeGameJpaEntity.getPrice());
        game.setDescription(storeGameJpaEntity.getDescription());
        // Add reviews if present
        if (!storeGameJpaEntity.getReviews().isEmpty()) {
            List<Review> reviewList = storeGameJpaEntity.getReviews()
                    .stream()
                    .map(reviewJpaEntity -> new Review(
                            reviewJpaEntity.getReviewId(),
                            new CustomerId(reviewJpaEntity.getCustomerId()),
                            new GameId(reviewJpaEntity.getGameId()),
                            reviewJpaEntity.getRating(),
                            reviewJpaEntity.getComment(),
                            reviewJpaEntity.getCreatedAt()
                    ))
                    .toList();
            game.setReviews(reviewList);
        }
        return game;
    }

    @Override
    public void createStoreGame(StoreGame newStoreGame) {

        StoreGameJpaEntity jpaEntity= new StoreGameJpaEntity(
                newStoreGame.getGameId().id(),
                newStoreGame.getGameName(),
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
