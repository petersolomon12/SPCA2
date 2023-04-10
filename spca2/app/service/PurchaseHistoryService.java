package service;

import com.google.inject.Inject;
import models.PurchaseHistory;
import models.User;
import play.data.FormFactory;
import repository.ProductRepos;
import repository.PurchaseHistoryRepos;
import repository.UserRepos;

public class PurchaseHistoryService {

    private final UserRepos userRepos;
    private final PurchaseHistoryRepos purchaseHistoryRepos;
    private final FormFactory formFactory;


    @Inject
    public PurchaseHistoryService(FormFactory formFactory, UserRepos userRepos, PurchaseHistoryRepos purchaseHistoryRepos) {
        this.userRepos= userRepos;
        this.formFactory = formFactory;
        this.purchaseHistoryRepos = purchaseHistoryRepos;
    }

    public void insertPurchase(PurchaseHistory purchaseHistory, User user){
        PurchaseHistory existingPurchase = purchaseHistoryRepos.insertPurchaseHistory(purchaseHistory);
        User existingUser = userRepos.getUser(user.getId());

            existingUser.getPurchaseHistory().add(existingPurchase);
            userRepos.updateUser(existingUser);
    }


}
