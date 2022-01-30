package COSC1047.Five_A_Row_Online_Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * A server for a network 2-player Five In A Row game.
 * The main change is instead of passing *data* between the
 * client and server, I made a 5P (5-in-a-row protocol) which is totally
 * plain text, so you can test the game with Telnet (always a good idea.)
 * The strings that are sent in 5P are:
 *
 *  Client -> Server         ||  Server -> Client
 *  ----------------         ||  ----------------
 *  MOVE x y                 ||  MOVE x y
 *                           ||
 *=========================================================================
 * Big feature! It allows an unlimited number of pairs of players to play.
 */

public class Server {

    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {

    }// end of main

    public void startServer() throws Exception{
        ServerSocket listener = new ServerSocket(6666);
        System.out.println("五子棋 Server is Running");
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {

                        /** waiting for clients join */
                        Socket socket_1 = listener.accept();
                        System.out.println(socket_1+" joined game");
                        Socket socket_2 = listener.accept();
                        System.out.println(socket_2+" joined game");

                        /** input and out put stream for each client socket */
                        BufferedReader socket_1_in = new BufferedReader(new InputStreamReader(socket_1.getInputStream()));
                        PrintWriter socket_1_out = new PrintWriter(socket_1.getOutputStream(), true);
                        BufferedReader socket_2_in = new BufferedReader(new InputStreamReader(socket_2.getInputStream()));
                        PrintWriter socket_2_out = new PrintWriter(socket_2.getOutputStream(), true);

                        /** synchronization */
                        Thread thread_1 = new Thread(){
                            @Override
                            public void run(){
                                while (true){
                                    try {
                                        String data_from_1 = socket_1_in.readLine();
                                        System.out.println(">>> "+data_from_1);
                                        if (data_from_1.startsWith("MOVE")){
                                            Scanner strstream = new Scanner(data_from_1);
                                            strstream.next();
                                            int set_x = strstream.nextInt();
                                            int set_y = strstream.nextInt();
                                            System.out.println("<<< "+"MOVE "+set_x+" "+set_y);
                                            socket_2_out.println("MOVE "+set_x+" "+set_y);
                                            strstream.close();
                                        }
                                    }
                                    catch (Exception ex){}
                                }
                            }
                        };
                        thread_1.start();
                        Thread thread_2 = new Thread(){
                            @Override
                            public void run(){
                                while (true){
                                    try {
                                        String data_from_2 = socket_2_in.readLine();
                                        System.out.println(">>> "+data_from_2);
                                        if (data_from_2.startsWith("MOVE")){
                                            Scanner strstream = new Scanner(data_from_2);
                                            strstream.next();
                                            int set_x = strstream.nextInt();
                                            int set_y = strstream.nextInt();
                                            System.out.println("<<< "+"MOVE "+set_x+" "+set_y);
                                            socket_1_out.println("MOVE "+set_x+" "+set_y);
                                            strstream.close();
                                        }
                                    }
                                    catch (Exception ex){}
                                }
                            }
                        };
                        thread_2.start();

                    
                }
                catch (Exception ex){

                }
                finally {
                    //listener.close();
                }
            }
        };
        thread.start();

    }

}// end of Server
