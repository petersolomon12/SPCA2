package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import repository.UserRepos;

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
        User existingUsers = this.getUserByEmail(userObject);

        //Checked Database for user "" if result is null then user doesn't exist throw new Exception else check if usernames.equals password.
        //Throw exception if it doesn't match else return Users
        if (existingUsers == null){
            throw new Exception("No such user in database");
        } else if (!Objects.equals(userObject.password, existingUsers.password)) {
            throw new Exception("Password does not match");
        }

        return existingUsers;
    }

    public void deleteUser(Http.Request userRequest){
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();
        User existingUser = this.getUser(userObject);

        //if existingUser is null throws error has there is no user with that id!!
        if(existingUser == null){
            try {
                throw new Exception("User Doesn't exist!!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        userRepos.deleteUser(userObject);
    }

    public User updateUser(Http.Request userRequest) {
        User userObject = formFactory.form(User.class).bindFromRequest(userRequest).get();
        User existingUser = this.getUser(userObject);
        User checkUserEmail = this.getUserByEmail(userObject);

        //if existingUser is null throws error has there is no user with that id!!
        if(existingUser == null){
            try {
                throw new Exception("User doesn't exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //if checkUserEmail is not null throws error that user is already in database!
        else if(checkUserEmail != null){
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

    public User getExistingUser(Http.Request objectRequest) throws Exception {
        JsonNode postBody = objectRequest.body().asJson();
        //Check if field userId is inputted in body of request.
        if(!postBody.has("userId")){
            throw new Exception("No user id inputted");
        }

        String userId = postBody.get("userId").asText();
        UUID uuid = UUID.fromString(userId);

        user.setId(uuid);

        //Checks if there is existing User!!
        User existingUser = this.getUser(user);

        //If existing User is null throw exception
        if(existingUser  ==  null){
            try {
                throw new Exception("User doesn't exist in database");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return existingUser;
    }

    public User getUserByEmail(User user) {
        return userRepos.getUserByEmail(user.getEmail());
    }

}
