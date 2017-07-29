package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5555);
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println(socket);
            Connection connection = new Connection(socket);
            connection.start();
        }
    }
}
