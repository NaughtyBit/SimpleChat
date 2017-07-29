package model;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {
    private static Set<User> users = new HashSet<>();

    public static Set<User> getUsers() {
        return users;
    }

    public static void addNewUser(User user){
        users.add(user);
    }
}
