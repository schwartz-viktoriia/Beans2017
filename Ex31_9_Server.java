/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex31_9_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vschwartz
 */
public class Ex31_9_Server extends Application {
    static TextField tfMe = new TextField();
    static TextArea taChat = new TextArea();
    static DataOutputStream outputToClient = null;
    
    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox(2);
        taChat.setPrefRowCount(20);
        taChat.setEditable(false);
//        taChat.setStyle("-fx-highlight-fill: lightgray; "
//                + "-fx-highlight-text-fill: firebrick; -fx-font-size: 20px;");
        vBox.getChildren().addAll(new Label("Me: "), tfMe, 
                new Label("Chat:"), new ScrollPane(taChat));
        Scene scene = new Scene(vBox);
        primaryStage.setTitle("Ex31_9_Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        tfMe.setOnAction(e -> {
            try {
                String msg = tfMe.getText();
                outputToClient.writeUTF(msg);
                outputToClient.flush();
                Platform.runLater(() -> taChat.appendText("Me: " + msg + '\n'));
            } catch (Exception ex) {}
        });
        
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(6000);
         //       Platform.runLater(() -> taChat.appendText("Server started at" + new Date()));
                Socket socket = serverSocket.accept();
                Platform.runLater(() -> taChat.appendText("You have somebody to talk to!\n"));
                DataInputStream inputFromClient = new DataInputStream(
                    socket.getInputStream());
                outputToClient = new DataOutputStream(
                    socket.getOutputStream());
                
                while (true) {
                    String msg = inputFromClient.readUTF();
                    Platform.runLater(() -> taChat.appendText("\t\t" + msg + '\n'));
                    Platform.runLater(() -> taChat.selectRange(taChat.getText().length(), 
                        taChat.getText().length() + msg.length()));
                }
            } catch (IOException ex) {
                taChat.appendText(ex.toString());
            }
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
