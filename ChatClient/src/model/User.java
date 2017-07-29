package model;

public class User {

    private String pass = "";
    private String name = "";

    public static User createUser(String name, String pass){
        User user = getInstance();
        user.pass = pass;
        user.name = name;
        return user;
    }
    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
