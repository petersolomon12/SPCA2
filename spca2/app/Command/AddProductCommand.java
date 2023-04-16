package Command;

import Strategy.JsonUuid;
import Strategy.Uuid;
import models.Product;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.UserRepos;

import java.util.UUID;

public class AddProductCommand implements ProductCommand {
    private final Http.Request productRequest;
    private final ProductRepos productRepos;
    private final UserRepos userRepos;
    private final FormFactory formFactory;
    JsonUuid jsonUuid = JsonUuid.getInstance();



    public AddProductCommand(Http.Request productRequest, ProductRepos productRepos, UserRepos userRepos, FormFactory formFactory) {
        this.productRequest = productRequest;
        this.productRepos = productRepos;
        this.userRepos = userRepos;
        this.formFactory = formFactory;
    }

    //ADDED STRATEGY PATTERN
    @Override
    public Product execute() throws Exception {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();
        UUID uuid = jsonUuid.getUuid(productRequest);

        User existingUser = userRepos.getUser(uuid);
        productObject.setUser(existingUser);

        if(productRepos.getProductByName(productObject.getName(), productObject.getUser()) != null){
            throw new Exception("Already have a product with this name");
        }

        return productRepos.insertProduct(productObject);
    }
}
