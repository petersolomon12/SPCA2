package repository;

import com.google.inject.Inject;
import models.PurchaseHistory;
import models.Reviews;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.function.Function;

public class ReviewRepos {

    private final JPAApi jpaApi;

    @Inject
    public ReviewRepos(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }



    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    public Reviews insertReview(Reviews reviews){
        return wrap(entityManager -> insertReviews(entityManager, reviews));
    }

    private Reviews insertReviews(EntityManager entityManager, Reviews reviews) {
        entityManager.persist(reviews);
        return reviews;
    }
}
