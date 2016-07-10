/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex31_10_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author vschwartz
 */
public class Ex31_10_Server extends Application {
    TextArea taLog = new TextArea();
    ArrayList<String> names = new ArrayList<>();
    DataOutputStream toClient;
    Stack<Socket> users = new Stack<>();
    String msg = "-";
    
    @Override
    public void start(Stage primaryStage) {
        taLog.setEditable(false);
        Scene scene = new Scene(new ScrollPane(taLog), 300, 250);
        primaryStage.setTitle("Ex31_10_Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> taLog.appendText("MultiThread Server started at " 
                        + new Date() + '\n'));
                
                while (true) {
                    users.push(serverSocket.accept());

                    new Thread(() -> {
                        try {
                            DataInputStream fromClient = new DataInputStream(users.peek().getInputStream());
                            String user = fromClient.readUTF().toString();
                            names.add(user);
//                            for (Socket s: users) {
//                                    DataOutputStream toClient = new DataOutputStream(s.getOutputStream());
//                                    toClient.writeInt(names.size());
//                                    toClient.flush();
//                                }
                            Platform.runLater(() -> taLog.appendText(user + 
                                " joined session at " + new Date() + "\n"));
                            
                            while (true) {
                                msg = fromClient.readUTF().toString();
                                Platform.runLater(() -> taLog.appendText(msg + "\n"));
                                for (Socket s: users) {
                                    DataOutputStream toClient = new DataOutputStream(s.getOutputStream());
                                    toClient.writeUTF(msg);
                                    toClient.flush();
                                }
                            }
                        } catch (IOException ex) {}
                    }).start();
                } 
            } catch (IOException ex) {}
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
