package service;

import Command.AddProductCommand;
import Command.PurchaseCartCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Product;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.UserRepos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService {

    private final ProductRepos productRepos;
    private final UserRepos userRepos;
    private final FormFactory formFactory;


    @Inject
    public ProductService(FormFactory formFactory, ProductRepos productRepos, UserRepos userRepos) {
        this.productRepos= productRepos;
        this.formFactory = formFactory;
        this.userRepos = userRepos;
    }

    //Added Command Pattern to addProduct
        public Product addProduct(Http.Request productRequest) throws Exception {
        AddProductCommand addProductCommand = new AddProductCommand(productRequest, productRepos, userRepos, formFactory);

        return addProductCommand.execute();
    }

    public Product updateStock(Http.Request productRequest) {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();

        Product existingProduct = productRepos.getProduct(productObject);

        existingProduct.setStockLevel(existingProduct.getStockLevel() + productObject.getStockLevel());
        return productRepos.updateProduct(existingProduct);
    }

    public Product deleteProduct(Http.Request productRequest){
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();

        return productRepos.deleteProduct(productObject);
    }

    public List<Product> viewStocks(Http.Request productRequest) {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();

        return productRepos.allAdminStocks(productObject.getId());
    }
    public Product searchProduct(Http.Request productRequest) {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();
        UUID uuid = getUuid(productRequest);

        User existingUser = userRepos.getUser(uuid);
        productObject.setUser(existingUser);

        return productRepos.getProductByName(productObject.getName(), productObject.getUser());
    }

    public List <Product> allProducts(Http.Request productRequest){
        List <Product> activeProducts = new ArrayList<>();

        for (Product product : productRepos.allProducts()){
            if (product.getStockLevel() > 0){
                activeProducts.add(product);
            }
        }

        return activeProducts;
    }

    public Product getProduct(Product product){

        return productRepos.getProduct(product);
    }

    public static UUID getUuid(Http.Request cartRequest) {
        JsonNode postBody = cartRequest.body().asJson();

        String id = postBody.get("uuid").asText();
        return UUID.fromString(id);
    }

}
