package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.Review;

public interface ReviewCreatedPort {
    void createReview(Review review);

}
