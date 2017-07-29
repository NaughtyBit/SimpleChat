package view;

import controller.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class SignIn extends Application {

    Stage link;
    @Override
    public void start(Stage dialogStage) throws Exception {
        link = dialogStage;
        dialogStage.setWidth(300);
        dialogStage.setHeight(200);
        dialogStage.setTitle("register or sign in");
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button register = new Button("register");
        Button signIn = new Button("sign in");
        buttons.getChildren().addAll(register, signIn);
        box.getChildren().addAll(new Label("please chose:"), buttons);
        Scene scene = new Scene(box);
        dialogStage.setScene(scene);
        dialogStage.show();
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                registration(new Stage());
            }
        });
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                signIn(new Stage());
            }
        });

    }

    /**
    * names of types:
     * r = registration
     * e = registration error (user is already exist)
     * s = sucsses registration
     * l = login
     * n = notification
    * */

    private void registration(Stage primaryStage) {
        VBox root = new VBox();
        VBox forms = new VBox();
        HBox loginBox = new HBox();
        HBox passBox = new HBox();
        HBox passAgainBox = new HBox();
        TextField login = new TextField();
        PasswordField pass = new PasswordField();
        PasswordField passAgain = new PasswordField();
        loginBox.getChildren().addAll(new Label("login:\t  "), login);
        passBox.getChildren().addAll(new Label("your pass:  "), pass);
        passAgainBox.getChildren().addAll(new Label("pass again:"), passAgain);
        forms.getChildren().addAll(loginBox, passBox, passAgainBox);
        HBox logIn = new HBox();
        Label result = new Label("");
        Button ok = new Button("ok");
        Button cancel = new Button("cancel");
        ok.setAlignment(Pos.BOTTOM_CENTER);
        cancel.setAlignment(Pos.BOTTOM_CENTER);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pass.getText().equals(passAgain.getText())) {
                    result.setText("");
                    System.out.println("pass are equals");
                    Main.tryConnect("r", User.createUser(login.getText(), pass.getText()));
                    String status = Main.getStatus();
                    if (status.startsWith("/e")){
                        result.setText("User is already exist");
                    } else if (status.startsWith("/s")){
                        primaryStage.close();
                        User.createUser(login.getText(), pass.getText());
                        chatStage();
                    }
                } else result.setText("Passwords don't match");
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        logIn.getChildren().addAll(ok, cancel, result);
        root.getChildren().addAll(forms, logIn);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void signIn(Stage primaryStage) {
        VBox root = new VBox();
        VBox forms = new VBox();
        HBox loginBox = new HBox();
        HBox passBox = new HBox();
        TextField login = new TextField();
        PasswordField pass = new PasswordField();

        loginBox.getChildren().addAll(new Label("login:\t  "), login);
        passBox.getChildren().addAll(new Label("your pass:  "), pass);
        forms.setMaxHeight(100);
        HBox buttons = new HBox();
        Button ok = new Button("ok");
        Button cancel = new Button("cancel");
        ok.setAlignment(Pos.BOTTOM_CENTER);
        cancel.setAlignment(Pos.BOTTOM_CENTER);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.tryConnect("l", User.createUser(login.getText(), pass.getText()));
               String status = Main.getStatus();
                if (status.startsWith("/e")){
                    System.out.println("false user");
                    pass.setText("");
                }else if (status.startsWith("/s")) {
                    primaryStage.close();
                    chatStage();
                }
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        buttons.getChildren().addAll(ok, cancel);
        forms.getChildren().addAll(loginBox, passBox);
        root.getChildren().addAll(forms, buttons);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void chatStage(){
        System.out.println("start chat");
        link.close();
        Stage primeStage = new Stage();
        BorderPane root = new BorderPane();
        VBox list = new VBox();
        list.setPrefWidth(200);
        list.setAlignment(Pos.BASELINE_LEFT);
        VBox chat = new VBox();
        chat.setAlignment(Pos.BASELINE_RIGHT);
        HBox chatBox = new HBox();
        HBox sendBox = new HBox();
        sendBox.setSpacing(40);
        TextField text = new TextField();
        Button send  = new Button("send");
        Label chatName = new Label("chat");
        send.setAlignment(Pos.BOTTOM_RIGHT);
        text.setPrefSize(500, 25);
        text.setAlignment(Pos.BOTTOM_CENTER);
        Label messages = new Label();

        Label test= new Label();
        test.setText("ASfasdaggasd\nadsf\nasdfa");
        messages.setAlignment(Pos.TOP_LEFT);
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.connection.send("m", text.getText());
                text.setText("");
            }
        });

        UpdateMessage updateMessage = new UpdateMessage();

        messages.textProperty().bind(updateMessage.messageProperty());
        Thread th = new Thread(updateMessage);
        th.setDaemon(true);
        th.start();
        messages.setPrefSize(600, 800);
        chatBox.getChildren().addAll(chatName, messages);
        list.getChildren().add(test);
        sendBox.getChildren().addAll(text, send);
        root.setPrefSize(800, 900);
        chat.getChildren().addAll(chatBox, sendBox);
        root.setLeft(list);
        root.setRight(chat);

        Scene scene = new Scene(root);
        primeStage.setScene(scene);
        primeStage.show();
    }

    public void launchKek(String[] args) {
        super.launch(args);
    }
}

class UpdateMessage extends Task<Void>{
String res = "";
    @Override
    protected Void call() throws Exception {
        while (true){
            if (false)break;
            res+="\n" + Main.connection.getMessage();
            updateMessage(res);
        }

        return null;
    }
}
