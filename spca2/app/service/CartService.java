package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Cart;
import models.Product;
import models.PurchaseHistory;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.CartRepos;
import repository.ProductRepos;

import java.util.UUID;

public class CartService {


    private final ProductRepos productRepos;
    private final PurchaseHistoryService purchaseHistoryService;
    private final ProductService productService;
    private final CartRepos cartRepos;
    private final FormFactory formFactory;


    @Inject
    public CartService(FormFactory formFactory, ProductRepos productRepos, CartRepos cartRepos, PurchaseHistoryService purchaseHistoryService, ProductService productService) {
        this.productRepos= productRepos;
        this.formFactory = formFactory;
        this.cartRepos = cartRepos;
        this.purchaseHistoryService = purchaseHistoryService;
        this.productService = productService;
    }

    public Cart addItems(Http.Request cartRequest) {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        JsonNode postBody = cartRequest.body().asJson();
        User user = new User();
        Product product = new Product();

        UUID uuid = getUuid(cartRequest);
        user.setId(uuid);

        String id = postBody.get("productUuid").asText();
        product.setId(UUID.fromString(id));

        cartObject.getProduct().add(productService.getProduct(product));

        if (cartRepos.getUserCart(user) == null){
            cartRepos.insertCart(cartObject);
        } else {
            Cart existingCart = cartRepos.getUserCart(user);
            for (Product cartProduct : cartObject.getProduct()){
                existingCart.getProduct().add(cartProduct);
            }
            cartRepos.updateCart(existingCart);
        }

        return cartObject;
    }


    public Cart getUserCart(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = getUuid(cartRequest);

        cartObject.getUser().setId(uuid);

        return getExistingCart(cartObject);
    }

    public Cart removeItem(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        Product product = new Product();

        UUID uuid = getUuid(cartRequest);

        product.setId(uuid);

        Cart existingCart = cartRepos.getCart(cartObject);

        for (Product existingProduct :  existingCart.getProduct()){
            if (existingProduct.getId() == product.getId())
                existingCart.getProduct().remove(existingProduct);
        }


        return cartRepos.updateCart(existingCart);
    }

    public Cart purchaseItem(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        User user = new User();

        /*
        Will have a field for json to get user id
        */
        for (Product existingProduct :  cartObject.getProduct()){
            existingProduct.setStockLevel(existingProduct.getStockLevel() -1);
            productRepos.updateProduct(existingProduct);
        }

        purchaseHistory.setProduct(cartObject.getProduct());

        purchaseHistoryService.insertPurchase(purchaseHistory, user);

        return cartRepos.deleteCart(cartObject);
    }


    private Cart getExistingCart(Cart cartObject) throws Exception {
        Cart existingCart = cartRepos.getUserCart(cartObject.getUser());

        if (existingCart == null){
            throw new Exception("No items in cart");
        }
        return existingCart;
    }

    private static UUID getUuid(Http.Request cartRequest) {
        JsonNode postBody = cartRequest.body().asJson();

        String id = postBody.get("uuid").asText();
        return UUID.fromString(id);
    }
}
