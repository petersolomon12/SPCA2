package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.UserRepos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService {

    private final UserRepos userRepos;
    private final  FormFactory formFactory;
    private final User user = new User();


    @Inject
    public UserService(FormFactory formFactory, UserRepos userRepos) {
        this.userRepos= userRepos;
        this.formFactory = formFactory;
    }

    public User addUser(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();

        if(Objects.equals(userObject.email, "")){
            throw new Exception("Email is null can't register");
        }
        //check if user email address already exist
        User existingUserByEmail = this.getUserByEmail(userObject);

        if(existingUserByEmail != null){
            //complain that user already exist
            try {
                throw new Exception("User already exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return userRepos.insertUser(userObject);
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

        System.out.println(userObject);

        User existingUser = userRepos.getUser(userObject.getId());

        userRepos.deleteUser(existingUser);
    }

    public User updateUser(Http.Request userRequest) throws Exception {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();


        User existingUser = userCheck(userObject);


        User checkUserEmail = this.getUserByEmail(userObject);


        //if checkUserEmail is not null throws error that user is already in database!
         if(checkUserEmail != null){
            try {
                throw new Exception("You can't update this user, use a different email!!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

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
    public List<User> allCustomers() {
        List <User> allCustomers = new ArrayList<>();

        List <User> allUsers = userRepos.allUsers();

        for (User user : allUsers){
            if (user.getUserType().equals(User.UserType.CUSTOMER)){
                allCustomers.add(user);
            }
        }
        return allCustomers;
    }

}
