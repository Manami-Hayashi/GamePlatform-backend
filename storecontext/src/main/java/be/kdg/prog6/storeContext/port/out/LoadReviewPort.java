package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.Review;

import java.util.List;
import java.util.UUID;

public interface LoadReviewPort {
    List<Review> findReviewByGameId(UUID gameId);
}
