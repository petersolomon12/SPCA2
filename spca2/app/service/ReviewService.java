package service;

import Strategy.JsonUuid;
import Visitor.ReviewVisitor;
import com.google.inject.Inject;
import models.Product;
import models.Reviews;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.ReviewRepos;

import java.util.UUID;

public class ReviewService {

    private final ReviewRepos reviewRepos;
    private final ProductService productService;
    private final ProductRepos productRepos;
    private final FormFactory formFactory;

    JsonUuid jsonUuid = JsonUuid.getInstance();



    @Inject
    public ReviewService(FormFactory formFactory, ReviewRepos reviewRepos, ProductService productService, ProductRepos productRepos) {
        this.reviewRepos = reviewRepos;
        this.formFactory = formFactory;
        this.productService = productService;
        this.productRepos = productRepos;
    }

    //ADDED STRATEGY PATTERN, VISITOR PATTERN
    public Reviews addReview(Http.Request reviewRequest){
        Reviews reviewObject = formFactory.form(Reviews.class).bindFromRequest(reviewRequest).get();

        UUID uuid = jsonUuid.getUuid(reviewRequest);
        Product product = new Product();
        product.setId(uuid);
        Product existingProduct = productService.getProduct(product);

        ReviewVisitor reviewVisitor = new ReviewVisitor(reviewObject, reviewRepos);

        existingProduct.accept(reviewVisitor);

        productRepos.updateProduct(existingProduct);

        return reviewVisitor.getPersistedReview();
    }



}
