package service;

import com.google.inject.Inject;
import models.Product;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.UserRepos;

import java.util.List;

public class ProductService {

    private final ProductRepos productRepos;
    private final FormFactory formFactory;
    private final User user = new User();


    @Inject
    public ProductService(FormFactory formFactory, ProductRepos productRepos) {
        this.productRepos= productRepos;
        this.formFactory = formFactory;
    }

    public Product addProduct(Http.Request productRequest) throws Exception {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();

        if(productRepos.getProductByName(productObject.getName()) != null){
            throw new Exception("Already have a product with this name");
        }

        return productRepos.insertProduct(productObject);
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

        return productRepos.getProductByName(productObject.getName());
    }

}
