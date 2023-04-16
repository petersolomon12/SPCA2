package service;

import Command.AddProductCommand;
import Factory.ProductFactory;
import Iterator.AvailProductIterator;
import Strategy.JsonUuid;
import Strategy.Uuid;
import com.google.inject.Inject;
import models.Product;
import models.User;
import net.bytebuddy.implementation.bind.annotation.Super;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.UserRepos;

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

    //ADDED STRATEGY PATTERN, FACTORY PATTERN
    public Product searchProduct(Http.Request productRequest) {
        ProductFactory productFactory = new ProductFactory(userRepos, formFactory);
        Product productObject = productFactory.createProduct(productRequest);

        return productRepos.getProductByName(productObject.getName(), productObject.getUser());
    }

    //Added Iterator Pattern
    public List <Product> allProducts(Http.Request productRequest){
        AvailProductIterator productIterator = new AvailProductIterator(productRepos.allProducts());

        return productIterator.getActiveProducts();
    }

    public Product getProduct(Product product){

        return productRepos.getProduct(product);
    }

}
