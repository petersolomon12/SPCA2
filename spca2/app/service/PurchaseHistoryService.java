package service;

import Facade.PurchasingHistoryFacade;
import com.google.inject.Inject;
import models.Cart;
import play.data.FormFactory;
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

    //ADDED FACADE PATTERN
    public void insertPurchase(Cart cart){
        PurchasingHistoryFacade purchasingHistoryFacade = new PurchasingHistoryFacade(purchaseHistoryRepos, userRepos);
        purchasingHistoryFacade.insertPurchase(cart);
    }
}
