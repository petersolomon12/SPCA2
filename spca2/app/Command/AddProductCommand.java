package Command;

import models.Product;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.UserRepos;

import java.util.UUID;

import static service.ProductService.getUuid;

public class AddProductCommand implements ProductCommand {
    private final Http.Request productRequest;
    private final ProductRepos productRepos;
    private final UserRepos userRepos;
    private final FormFactory formFactory;


    public AddProductCommand(Http.Request productRequest, ProductRepos productRepos, UserRepos userRepos, FormFactory formFactory) {
        this.productRequest = productRequest;
        this.productRepos = productRepos;
        this.userRepos = userRepos;
        this.formFactory = formFactory;
    }


    @Override
    public Product execute() throws Exception {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();
        UUID uuid = getUuid(productRequest);

        User existingUser = userRepos.getUser(uuid);
        productObject.setUser(existingUser);

        if(productRepos.getProductByName(productObject.getName(), productObject.getUser()) != null){
            throw new Exception("Already have a product with this name");
        }

        return productRepos.insertProduct(productObject);
    }
}
