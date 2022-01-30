package COSC1047.Five_A_Row_Online_Socket;

/* javafx API */
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/* socket connection API */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

public class Client extends Application{

	private int PORT = 6666;
	private Socket socket;
	private BufferedReader socket_in;
	private PrintWriter socket_out;

	Color emptyColor = new Color(0,0,0,0);
	Color white = Color.WHITE;
	Color black = Color.BLACK;
	Color over = Color.AQUA;
	private GameLogicKernel kernel = new GameLogicKernel(false);
	Circle[][] stones = new Circle[kernel.SIZE][kernel.SIZE];
	boolean gameOver = false;
	TextField player1_field = new TextField();
	TextField player2_field = new TextField();

	/** set host-client mode */
	public void setHost(){
		kernel.yourTurn = true;
	}

	// FIXME: 2017-04-17
	/** refresh game board manually */
	private void refresh(){
		for(int x=0; x<kernel.SIZE; x++){
			for(int y=0; y<kernel.SIZE; y++){
				Circle tmp = stones[x][y];
				int data = kernel.board[x][y];
				switch(data){
					case 0: tmp.setFill(emptyColor); break;
					case 1: tmp.setFill(white); break;
					case 2: tmp.setFill(black); break;
				}
			}
		}
		if(kernel.detectWhiteWin() && gameOver == false){
			if (player1_field.getText().length() == 0)
				winView("WHITE");
			else
				winView(player1_field.getText());
			gameOver = true;
		}
		else if(kernel.detectBlackWin() && gameOver == false){
			if (player2_field.getText().length() == 0)
				winView("BLACK");
			else
				winView(player2_field.getText());
			gameOver = true;
		}
	}

	// FIXME: 2017-04-17
	/** show win window */
	private void winView(String winnerName){
		Label winner = new Label(winnerName+" win!!");
		winner.setFont(new Font("Arial",40));
		winner.setTextFill(Color.RED);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(winner);
		Scene scene = new Scene(stackPane,500,300);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * synchronize method, to send data to server and get data from server
	 * */
	public void sync_to_server(int x, int y){
		socket_out.println("MOVE "+x+" "+y);
	}

	/**
	 * Runs the client as an application.
	 */
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}// end of start

	/** show client view */
	public void showClientView(String serverIP) throws Exception{

		/** Setup networking */
		socket = new Socket(serverIP, PORT);
		socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socket_out = new PrintWriter(socket.getOutputStream(), true);

		/** show offline game view */
		Label label1 = new Label("   White: ");
		Label label2 = new Label("   Black: ");
		GridPane boardPane = new GridPane();
		boardPane.setStyle("-fx-background-color: GRAY");
		boardPane.setGridLinesVisible(true);
		boardPane.setVgap(2);
		boardPane.setHgap(2);
		for(int x=0; x<kernel.SIZE; x++) {
			for (int y = 0; y < kernel.SIZE; y++) {
				stones[x][y] = new Circle();
				stones[x][y].setRadius(15);
				stones[x][y].setFill(emptyColor);
				boardPane.add(stones[x][y], x, y, 1, 1);

				int X = x;
				int Y = y;

				stones[x][y].setOnMouseClicked(e -> {
					if(kernel.place(X, Y)){
						sync_to_server(X,Y);
					}
					refresh();
				});

				stones[x][y].setOnMouseEntered(e -> {
					stones[X][Y].setFill(over);
				});

				stones[x][y].setOnMouseExited(e -> {
					refresh();
				});

			}
		}
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		hbox.getChildren().addAll(label1,player1_field,label2,player2_field);
		vbox.getChildren().addAll(hbox,boardPane);
		Stage stage = new Stage();
		Scene scene = new Scene(vbox);
		stage.setTitle("Game");
		stage.setScene(scene);
		stage.show();

		Thread syncThread = new Thread(){
			@Override
			public void run(){
				try {
					while(true){
						String data = socket_in.readLine();
						System.out.println(data);
						if (data.startsWith("MOVE")){
							Scanner strstream = new Scanner(data);
							strstream.next();
							int get_x = strstream.nextInt();
							int get_y = strstream.nextInt();
							System.out.println(get_x);
							System.out.println(get_y);
							kernel.set(get_x,get_y);
							strstream.close();
						}
					}
				}
				catch (Exception ex){}
			}
		};
		syncThread.start();
	}

}// end of class
