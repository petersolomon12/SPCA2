package controllers;

import com.google.inject.Inject;
import models.Cart;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import service.CartService;

import javax.transaction.Transactional;

import static play.mvc.Results.ok;

public class CartController {

    private CartService cartService;

    @Inject
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @Transactional
    public Result addItems(Http.Request request) throws Exception {
        Cart cart = cartService.addItems(request);
        return ok(Json.toJson(cart));
    }

    @Transactional
    public Result getUserCart(Http.Request request) throws Exception {
        Cart cart = cartService.getUserCart(request);
        return ok(Json.toJson(cart));
    }

    @Transactional
    public Result removeItem(Http.Request request) throws Exception {
        Cart cart = cartService.removeItem(request);
        return ok(Json.toJson(cart));
    }

    @Transactional
    public Result purchaseItem(Http.Request request) throws Exception {
        Cart cart = cartService.purchaseItem(request);
        return ok(Json.toJson(cart));
    }

}
