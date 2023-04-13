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
import repository.UserRepos;

import java.util.*;

public class CartService {


    private final ProductRepos productRepos;
    private final PurchaseHistoryService purchaseHistoryService;
    private final ProductService productService;
    private final CartRepos cartRepos;
    private final UserRepos userRepos;
    private final FormFactory formFactory;


    @Inject
    public CartService(FormFactory formFactory, ProductRepos productRepos, CartRepos cartRepos, UserRepos userRepos, PurchaseHistoryService purchaseHistoryService, ProductService productService) {
        this.productRepos = productRepos;
        this.formFactory = formFactory;
        this.cartRepos = cartRepos;
        this.userRepos = userRepos;
        this.purchaseHistoryService = purchaseHistoryService;
        this.productService = productService;
    }

    //ADDED BUILDER FACTORY
    public Cart addItems(Http.Request cartRequest) {
        UUID uuid = getUuid(cartRequest);

        CartBuilder cartBuilder = new CartBuilder()
                .setUser(userRepos.getUser(uuid))
                .addProductFromRequest(cartRequest);

        Cart cartObject = cartBuilder.build();

        if (cartRepos.getUserCart(cartObject.getUser()) == null) {
            cartRepos.insertCart(cartObject);

        } else {
            Cart existingCart = cartRepos.getUserCart(cartObject.getUser());
            for (Product cartProduct : cartObject.getProduct()) {
                existingCart.getProduct().add(cartProduct);
            }
            cartRepos.updateCart(existingCart);
        }

        return cartObject;
    }

    private void setProduct(Cart cartObject, Http.Request cartRequest) {
        JsonNode postBody = cartRequest.body().asJson();
        Product product = new Product();
        String id = postBody.get("productUuid").asText();
        product.setId(UUID.fromString(id));
        Set<Product> existingProductList = new HashSet<>();
        existingProductList.add(productService.getProduct(product));
        cartObject.setProduct(existingProductList);
    }


    public Cart getUserCart(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = getUuid(cartRequest);

        cartObject.setUser(userRepos.getUser(uuid));

        return getExistingCart(cartObject);
    }

    public Cart removeItem(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = getUuid(cartRequest);
        Product product = new Product();
        product.setId(uuid);

        product = productService.getProduct(product);

        Cart existingCart = cartRepos.getCart(cartObject);

        Iterator<Product> iterator = existingCart.getProduct().iterator();
        while (iterator.hasNext()) {
            Product existingProduct = iterator.next();
            if (existingProduct.getId().equals(product.getId())) {
                iterator.remove();
                break;
            }
        }

        return cartRepos.updateCart(existingCart);
    }

    public Cart purchaseItem(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = getUuid(cartRequest);
        cartObject.setUser(userRepos.getUser(uuid));

        /*
        Will have a field for json to get user id
        */
        for (Product existingProduct : cartRepos.getCart(cartObject).getProduct()) {
            existingProduct.setStockLevel(existingProduct.getStockLevel() - 1);
            productRepos.updateProduct(existingProduct);
        }

        purchaseHistoryService.insertPurchase(getExistingCart(cartObject));

        return cartRepos.deleteCart(cartRepos.getCart(cartObject));
    }


    private Cart getExistingCart(Cart cartObject) throws Exception {
        Cart existingCart = cartRepos.getUserCart(cartObject.getUser());

        if (existingCart == null) {
            throw new Exception("No items in cart");
        }
        return existingCart;
    }

    private static UUID getUuid(Http.Request cartRequest) {
        JsonNode postBody = cartRequest.body().asJson();

        String id = postBody.get("uuid").asText();
        return UUID.fromString(id);
    }

    private class CartBuilder {
        private User user;
        private Set<Product> products;

        public CartBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public CartBuilder addProduct(Product product) {
            if (products == null) {
                products = new HashSet<>();
            }
            products.add(product);
            return this;
        }

        public CartBuilder addProductFromRequest(Http.Request cartRequest) {
            JsonNode postBody = cartRequest.body().asJson();
            Product product = new Product();
            String id = postBody.get("productUuid").asText();
            product.setId(UUID.fromString(id));
            product = productService.getProduct(product);
            return addProduct(product);
        }

        public Cart build() {
            return new Cart(products, user);
        }
    }
}
