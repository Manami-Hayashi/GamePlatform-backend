package be.kdg.prog6.storeContext.adapter.out;

import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.PlayerId;
import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.port.out.LoadReviewPort;
import be.kdg.prog6.storeContext.port.out.ReviewCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ReviewDBAdapter implements ReviewCreatedPort, LoadReviewPort {
    private final ReviewJpaRepository reviewJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewDBAdapter.class);

    public ReviewDBAdapter(ReviewJpaRepository reviewJpaRepository) {
        this.reviewJpaRepository = reviewJpaRepository;
    }

    @Override
    public List<Review> findReviewByGameId(UUID gameId) {
        List<ReviewJpaEntity> reviewJpaEntities = reviewJpaRepository.findByGameId(gameId);
        if (reviewJpaEntities.isEmpty()) {
            logger.info("No reviews found for game with id: {}", gameId);
            return List.of();
        }

        List<Review> reviewList = reviewJpaEntities.stream()
                .map(this::toDomain)
                .toList();
        logger.info("Getting reviews for game with id: {}", gameId);
        return reviewList;
    }

    private Review toDomain(ReviewJpaEntity reviewJpaEntity) {
        return new Review(
                reviewJpaEntity.getReviewId(),
                new PlayerId(reviewJpaEntity.getPlayerId()),
                new GameId(reviewJpaEntity.getGameId()),
                reviewJpaEntity.getRating(),
                reviewJpaEntity.getComment(),
                reviewJpaEntity.getCreatedAt()
        );
    }

    @Override
    public void createReview(Review review) {
        ReviewJpaEntity jpaEntity = new ReviewJpaEntity(
                review.getReviewId(),
                review.getPlayerId().id(),
                review.getGameId().id(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
        logger.info("saving review with id: {}", review.getReviewId());
        reviewJpaRepository.save(jpaEntity);
    }
}
