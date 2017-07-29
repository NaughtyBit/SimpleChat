package controller;

import view.SignIn;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static Connection connection;

    public static void main(String... args) throws Exception {
        connection = new Connection();
        SignIn signIn = new SignIn();
        signIn.launchKek(args);
    }

    public static void tryConnect(String type, Object obj) {
            connection.send(type, obj);
    }
    public static String getJson(){
        return (String)connection.receive(String.class);
    }
    public static String getStatus(){
        return connection.status();
    }
}
