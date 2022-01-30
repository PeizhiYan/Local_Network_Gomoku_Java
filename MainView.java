package COSC1047.Five_A_Row_Online_Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends Application{
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		/** User Insterface */
		Button onlineModeButton = new Button("Online  Mode");
		onlineModeButton.setTextFill(Color.BROWN);
		onlineModeButton.setFont(new Font("Times new Roman",30));
		HBox hbox = new HBox();
		hbox.getChildren().addAll(onlineModeButton);
		Scene scene = new Scene(hbox);
		primaryStage.setTitle("Matthew");
		primaryStage.setScene(scene);
		primaryStage.show();
		/** event actions */
		onlineModeHandler onlineHandler = new onlineModeHandler();
		onlineModeButton.setOnAction(onlineHandler);

	}// end of start
	
	private class onlineModeHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			MenuView menuView = new MenuView();
			menuView.showMenuView();
		}
	}// end of offline handler

}// end of mainView
