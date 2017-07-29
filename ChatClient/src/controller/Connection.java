package controller;

import com.google.gson.Gson;
import view.SignIn;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;


    public Connection() throws IOException {
        socket = new Socket("localhost", 5555);
        System.out.println(socket);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        gson = new Gson();
    }


    public void send(String type, Object obj) {
        try {
            String str = "/" + type + "/" + gson.toJson(obj).replace("\n", "");
            System.out.println("отправил : " + str);
            out.writeUTF(str);
            out.flush();
        }catch (Exception e){

        }
    }
    public String getMessage() {
        try {
            String res = in.readUTF();
            System.out.println("получил: "+ res);
            return res;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public Object receive(Type clazz) {
        Object gsonObj = null;
        try {
            String input = in.readUTF();
            System.out.println("принял: " + input);
            gsonObj = gson.fromJson(input, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gsonObj;
    }

    public String status(){
        try {
            String input = in.readUTF();
            System.out.println("принял: " + input);
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/!/";
    }
}
