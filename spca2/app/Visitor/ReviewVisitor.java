package Visitor;

import models.Product;
import models.Reviews;
import repository.ReviewRepos;

public class ReviewVisitor implements ProductVisitor {
    private final Reviews review;
    private final ReviewRepos reviewRepos;
    private Reviews persistedReview;

    public ReviewVisitor(Reviews review, ReviewRepos reviewRepos) {
        this.review = review;
        this.reviewRepos = reviewRepos;
    }

    @Override
    public void visit(Product product) {
        persistedReview = reviewRepos.insertReview(review);
        product.getReviews().add(persistedReview);
    }

    public Reviews getPersistedReview() {
        return persistedReview;
    }
}