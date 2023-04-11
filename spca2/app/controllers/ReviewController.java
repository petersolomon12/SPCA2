package controllers;

import com.google.inject.Inject;
import models.Product;
import models.Reviews;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import service.ProductService;
import service.ReviewService;

import javax.transaction.Transactional;

import static play.mvc.Results.ok;

public class ReviewController {

    private ReviewService reviewService;

    @Inject
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @Transactional
    public Result addReview(Http.Request request) throws Exception {
        Reviews reviews = reviewService.addReview(request);
        return ok(Json.toJson(reviews));
    }
}
