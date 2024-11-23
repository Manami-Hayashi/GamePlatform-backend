package be.kdg.storeContext.port.out;

import be.kdg.storeContext.domain.Review;

import java.util.List;
import java.util.UUID;

public interface LoadReviewPort {
    List<Review> findReviewByGameId(UUID gameId);
}
