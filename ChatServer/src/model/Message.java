package model;

import java.util.Date;

public class Message {
    private String line;
    private Date date;
    private User sender;

    public String getLine() {
        return line;
    }

    public Date getDate() {
        return date;
    }

    public User getSender() {
        return sender;
    }

    public Message(String line, User sender) {
        date = new Date();
        this.line = line;
        this.sender = sender;
    }
}
