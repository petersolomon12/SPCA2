package Decorator;

import models.User;
import repository.UserRepos;

public class DupUserValidator implements UserValidator{

    private final UserRepos userRepos;

    public DupUserValidator(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    @Override
    public User validateUser(User user) throws Exception {
        User existingUserByEmail = userRepos.getUserByEmail(user.email);
        if (existingUserByEmail != null) {
            throw new Exception("User already exists");
        }
        return user;
    }
}
