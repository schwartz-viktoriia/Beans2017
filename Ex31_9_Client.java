/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex31_9_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Ex31_9_Client extends Application {
    static TextField tfMe = new TextField();
    static TextArea taChat = new TextArea(); 
    static DataOutputStream outputToServer = null;
    
    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox(2);
        taChat.setPrefRowCount(20);
        taChat.setEditable(false);
        vBox.getChildren().addAll(new Label("Me: "), tfMe, 
                new Label("Chat:"), new ScrollPane(taChat));
        Scene scene = new Scene(vBox);
        
        primaryStage.setTitle("Ex31_9_Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        tfMe.setOnAction(e -> {
            try {
                String msg = tfMe.getText();
                outputToServer.writeUTF(msg);
                outputToServer.flush();
                Platform.runLater(() -> taChat.appendText("Me: " + msg + '\n'));
            } catch (Exception ex) {}
        });
        
        new Thread (() -> {
            try {
                Socket socket = new Socket("localhost", 6000);
                DataInputStream inputFromServer = new DataInputStream(
                    socket.getInputStream());
                outputToServer = new DataOutputStream(
                    socket.getOutputStream());
                
                while (true) {
                    String msg = inputFromServer.readUTF();
                    Platform.runLater(() -> taChat.appendText("\t\t" + msg + '\n'));
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
