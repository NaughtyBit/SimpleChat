package model;

public class User {
    private String pass = "";
    private String name = "";

    public String getPass() {
        return pass;
    }

    public String getName() {

        return name;
    }

    public User(String pass, String name) {

        this.pass = pass;
        this.name = name;
    }
}
