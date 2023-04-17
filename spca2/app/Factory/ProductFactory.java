package Factory;

import Strategy.JsonUuid;
import models.Product;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.UserRepos;

import java.util.UUID;

public class ProductFactory {
    private final UserRepos userRepos;

    private final FormFactory formFactory;
    JsonUuid jsonUuid = JsonUuid.getInstance();


    public ProductFactory(UserRepos userRepos, FormFactory formFactory) {
        this.userRepos = userRepos;
        this.formFactory = formFactory;
    }

    public Product createProduct(Http.Request productRequest) {
        Product productObject = formFactory.form(Product.class).bindFromRequest(productRequest).get();
        UUID uuid = jsonUuid.getUuid(productRequest);

        User existingUser = userRepos.getUser(uuid);
        productObject.setUser(existingUser);

        return productObject;
    }
}

