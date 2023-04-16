package Decorator;

import models.User;

public interface UserValidator {
    User validateUser(User user) throws Exception;
}
