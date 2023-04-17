package controllers;

import com.google.inject.Inject;
import models.Product;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import service.ProductService;

import javax.transaction.Transactional;
import java.util.List;

import static play.mvc.Results.ok;

public class ProductController {

    private ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Transactional
    public Result addProduct(Http.Request request) throws Exception {
        Product product = productService.addProduct(request);
        return ok(Json.toJson(product));
    }

    @Transactional
    public Result updateStock(Http.Request request) throws Exception {
        Product product = productService.updateStock(request);
        return ok(Json.toJson(product));
    }   @Transactional


    public Result deleteProduct(Http.Request request) throws Exception {
        Product product = productService.deleteProduct(request);
        return ok(Json.toJson(product));
    }

    public Result viewAdminItems(Http.Request request) throws Exception {
        List<Product> product = productService.viewStocks(request);
        return ok(Json.toJson(product));
    }
    public Result searchProduct(Http.Request request) throws Exception {
        Product product = productService.searchProduct(request);
        return ok(Json.toJson(product));
    }

    public Result allProducts(Http.Request request) throws Exception {
        List <Product> product = productService.allProducts(request);
        return ok(Json.toJson(product));
    }
}
