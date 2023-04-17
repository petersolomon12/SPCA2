package repository;

import com.google.inject.Inject;
import models.PurchaseHistory;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.function.Function;

public class PurchaseHistoryRepos {

    private final JPAApi jpaApi;

    @Inject
    public PurchaseHistoryRepos(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }



    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    public PurchaseHistory insertPurchaseHistory(PurchaseHistory purchaseHistory){
        return wrap(entityManager -> insertPurchaseHistory(entityManager, purchaseHistory));
    }

    private PurchaseHistory insertPurchaseHistory(EntityManager entityManager, PurchaseHistory purchaseHistory) {
        entityManager.persist(purchaseHistory);
        return purchaseHistory;
    }
}
