package model;

import controller.Connection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chat {
    private List<Message> messages = new LinkedList<>();
    private List<Connection>connections = new ArrayList<>();

    public void connectToChat(Connection connection) throws IOException {
        for (Connection c : connections){
            c.sendDataToUsers("/n/", connection.getOnlineUser().getName() + " has entered to chat");
        }
        connections.add(connection);

    }

    public void addMessage(Message message) throws IOException {
        messages.add(message);
        for (Connection c : connections){
            System.out.println("sending message to " + c.getOnlineUser().getName());
            c.sendDataToUsers("/m/", new SimpleDateFormat("HH:mm:ss").format(message.getDate())+ "/" + message.getSender().getName() + "->" + message.getLine());
        }
    }
}
