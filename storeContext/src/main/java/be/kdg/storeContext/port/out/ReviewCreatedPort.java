package be.kdg.storeContext.port.out;

import be.kdg.storeContext.domain.Review;

public interface ReviewCreatedPort {
    void createReview(Review review);

}
