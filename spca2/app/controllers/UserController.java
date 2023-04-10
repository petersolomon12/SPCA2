package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.UserService;

import javax.transaction.Transactional;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {

    private final FormFactory formFactory;
    private UserService userService;

    @Inject
    public UserController(final FormFactory formFactory, UserService userService) {
        this.formFactory = formFactory;
        this.userService = userService;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */


    @Transactional
    public Result addUser(Http.Request request) throws Exception {
        User user = userService.addUser(request);
        return ok(Json.toJson(user));
    }

    @Transactional
    public Result deleteUser(Http.Request request){
        userService.deleteUser(request);
        return ok(Json.toJson("Completed"));
    }

    @Transactional
    public Result updateUser(Http.Request request) {
        User user = userService.updateUser(request);
        return ok(Json.toJson(user));
    }
    @Transactional
    public Result getUserInfo(Http.Request request) throws Exception {
        User user = userService.getExistingUser(request);
        return ok(Json.toJson(user));
    }

    @Transactional
    public Result userLogin(Http.Request request) throws Exception {
        User user = userService.userLogin(request);
        return ok(Json.toJson(user));
    }

}
