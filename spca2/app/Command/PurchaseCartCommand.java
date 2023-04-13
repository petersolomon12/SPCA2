package Command;

import models.Cart;
import models.Product;
import play.mvc.Http;
import repository.CartRepos;
import repository.ProductRepos;
import service.CartService;
import service.PurchaseHistoryService;


public class PurchaseCartCommand implements Command {
    private final Cart cartObject;
    private final CartRepos cartRepos;
    private final CartService cartService;
    private final ProductRepos productRepos;
    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseCartCommand(Cart cartObject, CartService cartService, CartRepos cartRepos, ProductRepos productRepos, PurchaseHistoryService purchaseHistoryService) {
        this.cartObject = cartObject;
        this.cartRepos = cartRepos;
        this.productRepos = productRepos;
        this.cartService = cartService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @Override
    public void execute() throws Exception {
        /*
        Will have a field for json to get user id
        */
        for (Product existingProduct : cartRepos.getCart(cartObject).getProduct()) {
            existingProduct.setStockLevel(existingProduct.getStockLevel() - 1);
            productRepos.updateProduct(existingProduct);
        }

        purchaseHistoryService.insertPurchase(cartService.getExistingCart(cartObject));

        cartRepos.deleteCart(cartRepos.getCart(cartObject));
    }
}



