package Flyweight;

import models.User;
import repository.UserRepos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flyweight {
    private final Map<User.UserType, List<User>> customers;
    private final UserRepos userRepos;

    public Flyweight(UserRepos userRepos) {
        this.customers = new HashMap<>();
        this.userRepos = userRepos;
    }


    public List<User> getUsersByType(User.UserType userType) {
        if (!customers.containsKey(userType)) {
            List<User> usersOfType = new ArrayList<>();

            List<User> allUsers = userRepos.allUsers();
            for (User user : allUsers) {
                if (user.getUserType().equals(userType)) {
                    usersOfType.add(user);
                }
            }

            customers.put(userType, usersOfType);
        }

        return customers.get(userType);
    }
}
