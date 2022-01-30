package COSC1047.Five_A_Row_Online_Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class MenuView extends Application{

    String localAddress = null;
    ServerSocket listener = null;
    Socket teller = null;

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){
        //showMenuView();
    }

    public void showMenuView(){
        /** layout */
        HBox menuBox = new HBox();
        Button hostBtn = new Button("Host a game");
        Button joinBtn = new Button("Join a game");
        hostBtn.setFont(new Font("Tines new Roman",30));
        joinBtn.setFont(new Font("Tines new Roman",30));
        menuBox.getChildren().addAll(hostBtn,joinBtn);
        Stage stage = new Stage();
        stage.setScene(new Scene(menuBox));
        stage.setTitle("Host or Participant?");
        stage.show();
        /** button actions */
        hostBtn.setOnAction(e ->{
            showHostView();
            stage.close();
            /*
            SocketConnectMode game = new SocketConnectMode();
            game.offlineView();
            */
        });
        joinBtn.setOnAction(e -> {
            showJoinView();
            stage.close();
            /*
            SocketConnectMode game = new SocketConnectMode();
            game.offlineView();
            */
        });
    }

    public void showHostView(){

        try {
            Server server = new Server();
            Client client = new Client();
            client.setHost();
            server.startServer();
            /** get local address */
            InetAddress ip = InetAddress.getLocalHost();
            localAddress = ip.getHostAddress();
            System.out.println(" local address: " + localAddress + " host name: " + ip.getHostName());
            showWaitingView();
            client.showClientView(localAddress);
        }
        catch (Exception ex){
            System.out.println("ERROR: Socket host cannot be established. ");
        }

    }// end of host view class

    public void showWaitingView(){
        /** show waiting view */
        VBox vb = new VBox();
        Label hostIP = new Label("Your IP: " + localAddress);
        hostIP.setFont(new Font("Tines new Roman",30));
        Label label2 = new Label("Waiting for participant join");
        label2.setFont(new Font("Tines new Roman",30));
        vb.getChildren().addAll(hostIP,label2);
        Stage stage = new Stage();
        stage.setTitle("Waiting for joining");
        stage.setScene(new Scene(vb));
        stage.show();
    }

    public void showJoinView(){
        /** show waiting view */
        VBox vb = new VBox();
        Label hostIP = new Label("Host IP (Example: 192.168.2.2)");
        hostIP.setFont(new Font("Tines new Roman",30));
        TextField ipField = new TextField();
        ipField.setFont(new Font("Tines new Roman",30));
        Button joinBtn = new Button("Join");
        joinBtn.setFont(new Font("Tines new Roman",30));
        vb.getChildren().addAll(hostIP,ipField,joinBtn);
        Stage stage = new Stage();
        stage.setTitle("Waiting for joining");
        stage.setScene(new Scene(vb));
        stage.show();
        /** actions */
        joinBtn.setOnAction(e -> {
            showParticipantView(ipField.getText());
        });
    }// end of join view class

    public void showParticipantView(String hostIp){
        try {
            Client client = new Client();
            client.showClientView(hostIp);
        }
        catch (Exception ex){
            System.out.println("ERROR: cannot reach host machine ");
        }
    }

}// end of Menu class
