/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex31_10_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vschwartz
 */
public class Ex31_10_Client extends Application {
    static String name = "";
    DataOutputStream toServer;
    DataInputStream fromServer;
    
    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Intoduce Yourself");
        dialog.setHeaderText("Welcome to char room");
        dialog.setContentText("Enter Username here: ");
        VBox vBox = new VBox(2);
        TextField tfMe = new TextField();
        TextArea taChat = new TextArea();
        taChat.setEditable(false);
        Label lChat = new Label();
        vBox.getChildren().addAll(new Label("Me"), tfMe, lChat, new ScrollPane(taChat));
        Scene scene = new Scene(vBox);
        name = dialog.showAndWait().get();
        primaryStage.setTitle(name);//"Ex31_10_Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        tfMe.setOnAction(e -> {
            try {
                String msg = tfMe.getText();
                toServer.writeUTF(name + ": " + msg);
                toServer.flush();
            } catch (Exception ex) {}
        });
        
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8000);
                toServer = new DataOutputStream(socket.getOutputStream());
                toServer.writeUTF(name);
                toServer.flush();
                fromServer = new DataInputStream(socket.getInputStream());
                while (true) {
                    String msg = fromServer.readUTF().toString();
                    Platform.runLater(() -> taChat.appendText(msg + '\n'));
                    
//                    int users = fromServer.readInt();
//                    lChat.setText("chat (" + users + ") users online");
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
