package controller;

import com.google.gson.Gson;
import model.Chat;
import model.Message;
import model.User;
import model.UserStorage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private String input = "", output = "";

    private static final Chat CHAT  = new Chat();
    private Socket userSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;
    private User onlineUser;

    private boolean isLogin = false;

    public Connection(Socket userSocket) throws IOException {
        this.userSocket = userSocket;
        in = new DataInputStream(userSocket.getInputStream());
        out = new DataOutputStream(userSocket.getOutputStream());
        gson = new Gson();
        System.out.println("connected");
    }

    public User getOnlineUser() {
        return onlineUser;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (isLogin) {
                 //   System.out.println("chatting");
                    input = in.readUTF();
                    System.out.println(input);
                    if (input.startsWith("/m")){
                        CHAT.addMessage(sendDataToChat(input.substring(3)));

                    }
                } else {
                //    System.out.println("start logining");

                    input = in.readUTF();
                   // System.out.println("принял: " + input);
                    if (input.startsWith("/l")) {
                        output = login(input, output);
                    } else if (input.startsWith("/r")) {
                        output = reg(input, output);
                    }
                   // System.out.println("отправил: " + output);
                }
            }

        } catch (IOException e) {

        }
    }

    private String  reg(String input, String output) throws IOException {
        User user = gson.fromJson(input.substring(3, input.length()), User.class);
        for (User u : UserStorage.getUsers()) {
            if (u.getName().equals(user.getName())) {
                output = "/e/";
                out.writeUTF(output);
                out.flush();
                continue;
            }
        }
        if (!output.equals("/e/")) {
            UserStorage.addNewUser(user);
            output = "/s/";
            this.onlineUser = user;
            isLogin = true;
            CHAT.connectToChat(this);

            out.writeUTF(output);
            out.flush();
        }
        return output;
    }

    private String login(String input, String output) throws IOException {
        User user = gson.fromJson(input.substring(3, input.length()), User.class);

        for (User u : UserStorage.getUsers()) {
            if (u.getName().equals(user.getName())) {
                if (u.getPass().equals(user.getPass())) {
                    output = "/s/";
                    isLogin = true;
                    onlineUser = user;
                    CHAT.connectToChat(this);
                } else output = "/e/";
                continue;
            }
        }

        out.writeUTF(output);
        out.flush();
        return output;
    }
    public Message sendDataToChat(String text){
        return new Message(text, onlineUser);
    }
    public void sendDataToUsers(String type, String data) throws IOException {
        output = type + data;
        out.writeUTF(output);
        out.flush();
    }

}
