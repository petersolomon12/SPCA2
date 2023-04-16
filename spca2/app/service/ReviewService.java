package service;

import Strategy.JsonUuid;
import Strategy.Uuid;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Product;
import models.Reviews;
import play.data.FormFactory;
import play.mvc.Http;
import repository.ProductRepos;
import repository.PurchaseHistoryRepos;
import repository.ReviewRepos;
import repository.UserRepos;

import java.util.UUID;

public class ReviewService {

    private final ReviewRepos reviewRepos;
    private final ProductService productService;
    private final ProductRepos productRepos;
    private final FormFactory formFactory;

    Uuid jsonUuid = new JsonUuid();



    @Inject
    public ReviewService(FormFactory formFactory, ReviewRepos reviewRepos, ProductService productService, ProductRepos productRepos) {
        this.reviewRepos = reviewRepos;
        this.formFactory = formFactory;
        this.productService = productService;
        this.productRepos = productRepos;
    }

    //ADDED STRATEGY PATTERN
    public Reviews addReview(Http.Request reviewRequest){
        Reviews reviewObject = formFactory.form(Reviews.class).bindFromRequest(reviewRequest).get();

        UUID uuid = jsonUuid.getUuid(reviewRequest);
        Product product = new Product();
        product.setId(uuid);
        Product existingProduct = productService.getProduct(product);

        Reviews persistReview = reviewRepos.insertReview(reviewObject);

        existingProduct.getReviews().add(persistReview);

        productRepos.updateProduct(existingProduct);

        return persistReview;
    }



}
