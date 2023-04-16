package Facade;

import models.Cart;
import models.Product;
import models.PurchaseHistory;
import models.User;
import repository.PurchaseHistoryRepos;
import repository.UserRepos;

import java.util.HashSet;
import java.util.Set;

public class PurchasingHistoryFacade {

    private PurchaseHistoryRepos purchaseHistoryRepos;
    private UserRepos userRepos;

    public PurchasingHistoryFacade(PurchaseHistoryRepos purchaseHistoryRepos, UserRepos userRepos) {
        this.purchaseHistoryRepos = purchaseHistoryRepos;
        this.userRepos = userRepos;
    }

    public void insertPurchase(Cart cart) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        Set<Product> existingProductList = new HashSet<>(cart.getProduct());
        purchaseHistory.setProduct(existingProductList);
        PurchaseHistory existingPurchase = purchaseHistoryRepos.insertPurchaseHistory(purchaseHistory);
        updatePurchaseHistory(cart.getUser(), existingPurchase);
    }

    private void updatePurchaseHistory(User user, PurchaseHistory purchaseHistory) {
        user.getPurchaseHistory().add(purchaseHistory);
        userRepos.updateUser(user);
    }
}
