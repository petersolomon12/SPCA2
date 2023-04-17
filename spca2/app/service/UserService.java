package service;

import Decorator.DupUserValidator;
import Decorator.EmailValidator;
import Decorator.UserValidator;
import Flyweight.Flyweight;
import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.UserRepos;

import java.util.List;
import java.util.Objects;

public class UserService {

    private final UserRepos userRepos;
    public final FormFactory formFactory;
    private final User user = new User();


    @Inject
    public UserService(FormFactory formFactory, UserRepos userRepos) {
        this.userRepos= userRepos;
        this.formFactory = formFactory;
    }

    public User addUser(Http.Request userRequest) throws Exception {
        User user = formFactory.form(User.class).bindFromRequest(userRequest).get();
        UserValidator emailValidator = new EmailValidator();
        UserValidator dupUserValidator = new DupUserValidator(userRepos);

        user = emailValidator.validateUser(user);
        user = dupUserValidator.validateUser(user);

        return userRepos.insertUser(user);
    }

    //getUser default is by id when not specified.
    public User getUser(User user) {
        return userRepos.getUser(user.getId());
    }

    public User userLogin(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();

        User existingUser = userCheck(userObject);

        if (!Objects.equals(userObject.password, existingUser.password)) {
            throw new Exception("Password does not match");
        }

        return existingUser;
    }

    private User userCheck(User userObject) throws Exception {
        User existingUser = this.getUserByEmail(userObject);

        //Checked Database for user "" if result is null then user doesn't exist throw new Exception else check if usernames.equals password.
        //Throw exception if it doesn't match else return Users
        if (existingUser == null){
            throw new Exception("No such user in database");
        }
        return existingUser;
    }

    public void deleteUser(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();

        User existingUser = userRepos.getUser(userObject.getId());

        userRepos.deleteUser(existingUser);
    }

    public User updateUser(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();

        UserValidator dupUserValidator = new DupUserValidator(userRepos);
        dupUserValidator.validateUser(userObject);

        return userRepos.updateUser(userObject);
    }

    public User updatedUser(User userObject){
        return userRepos.updateUser(userObject);
    }

    public List <User> allUsers(){
       return userRepos.allUsers();
    }

    public User getExistingUser(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();

        System.out.println(userObject);

        return userCheck(userObject);
    }

    public User getUserByEmail(User user) {
        return userRepos.getUserByEmail(user.getEmail());
    }
    public List<User> allCustomers() throws IllegalAccessException {
        Flyweight flyweight = new Flyweight(userRepos);
        return flyweight.getUsersByType(User.UserType.CUSTOMER);
    }

}
