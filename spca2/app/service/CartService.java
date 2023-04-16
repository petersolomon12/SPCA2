package service;

import Command.PurchaseCartCommand;
import Strategy.JsonUuid;
import Strategy.Uuid;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Cart;
import models.Product;
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

    Uuid jsonUuid = new JsonUuid();


    @Inject
    public CartService(FormFactory formFactory, ProductRepos productRepos, CartRepos cartRepos, UserRepos userRepos, PurchaseHistoryService purchaseHistoryService, ProductService productService) {
        this.productRepos = productRepos;
        this.formFactory = formFactory;
        this.cartRepos = cartRepos;
        this.userRepos = userRepos;
        this.purchaseHistoryService = purchaseHistoryService;
        this.productService = productService;
    }

    //ADDED BUILDER PATTERN, STRATEGY PATTERN
    public Cart addItems(Http.Request cartRequest) {
        UUID uuid = jsonUuid.getUuid(cartRequest);

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

    //ADDED STRATEGY PATTERN
    public Cart getUserCart(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = jsonUuid.getUuid(cartRequest);

        cartObject.setUser(userRepos.getUser(uuid));

        return getExistingCart(cartObject);
    }

    //Iterator Pattern, STRATEGY PATTERN
    public Cart removeItem(Http.Request cartRequest) {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = jsonUuid.getUuid(cartRequest);
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

    //Added Command Pattern to purchaseItem, STRATEGY PATTERN
    public Cart purchaseItem(Http.Request cartRequest) throws Exception {
        Cart cartObject = formFactory.form(Cart.class).bindFromRequest(cartRequest).get();
        UUID uuid = jsonUuid.getUuid(cartRequest);
        cartObject.setUser(userRepos.getUser(uuid));

        PurchaseCartCommand purchaseCommand = new PurchaseCartCommand(cartObject, this, cartRepos, productRepos, purchaseHistoryService);
        purchaseCommand.execute();
        return cartObject;
    }


    public Cart getExistingCart(Cart cartObject) throws Exception {
        Cart existingCart = cartRepos.getUserCart(cartObject.getUser());

        if (existingCart == null) {
            throw new Exception("No items in cart");
        }
        return existingCart;
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
        //ADDED STRATEGY PATTERN
        public CartBuilder addProductFromRequest(Http.Request cartRequest) {
            UUID id = jsonUuid.getProductUuid(cartRequest);
            Product product = new Product();
            product.setId(id);
            product = productService.getProduct(product);
            return addProduct(product);
        }

        public Cart build() {
            return new Cart(products, user);
        }
    }
}
