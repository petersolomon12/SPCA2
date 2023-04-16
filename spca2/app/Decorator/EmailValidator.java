package Decorator;

import models.User;

public class EmailValidator implements UserValidator{

    @Override
    public User validateUser(User user) throws Exception {
        if (user.email == null || user.email.isEmpty()) {
            throw new Exception("Email is null can't register");
        }
        return user;
    }
}
