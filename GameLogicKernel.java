package COSC1047.Five_A_Row_Online_Socket;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameLogicKernel {

	final static int SIZE = 15;// WIDTH == HEIGHT == SIZE
	public int[][] board = new int[SIZE][SIZE];// to store game board data
	boolean whiteTurn = true;// indicates whether it's white's turn
	boolean yourTurn = true;

	// FIXME: 2017-04-17
	/** constructor */
	public GameLogicKernel(Boolean yourTurn){
		this.yourTurn = yourTurn;
		reset();
	}

	// FIXME: 2017-04-17
	/** initialize game board array 
	 * ================================
	 * use 0 represents empty
	 * use 1 represents white stone
	 * use 2 represents black stone
	 * */
	public void reset(){
		whiteTurn = true;
		for(int x=0; x<SIZE; x++){
			for(int y=0; y<SIZE; y++){
				board[x][y] = 0;
			}
		}
	}// end of constructor

	// FIXME: 2017-04-17
	/** place a stone */
	public boolean place(int x, int y){
		if(board[x][y] == 0 && yourTurn == true){
			if(whiteTurn == true)
				board[x][y] = 1;
			else
				board[x][y] = 2;
			whiteTurn = !whiteTurn;
			yourTurn = !yourTurn;// block your further move until opponent make a move
			return true;// stone placed
		}
		return false;// can place stone here
	}

	public boolean set(int x, int y){
		if(board[x][y] == 0){
			if(whiteTurn == true)
				board[x][y] = 1;
			else
				board[x][y] = 2;
			whiteTurn = !whiteTurn;
			yourTurn = !yourTurn;// block your further move until opponent make a move
			return true;// stone placed
		}
		return false;// can place stone here
	}

	// FIXME: 2017-04-17
	/** detect white win */
	public boolean detectWhiteWin(){
		/* detect 5 in a row */
		int STONE = 1;
		for(int i=0; i<SIZE; i++){
			int counter = 0;
			boolean continous = true;
			for(int j=0; j<SIZE; j++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
		}
		/* detect 5 in a column */
		for(int j=0; j<SIZE; j++){
			int counter = 0;
			boolean continous = true;
			for(int i=0; i<SIZE; i++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
		}
		/* detect 5 in diagonals 
		 * */
		for(int z=0; z<SIZE; z++){
			int counter = 0;
			boolean continous = true;
			for(int i=z,j=0; i<SIZE&&j<SIZE; i++,j++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
			for(int i=0,j=z; i<SIZE&&j<SIZE; i++,j++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
		}
		for(int z=SIZE-1; z>=0; z--){
			int counter = 0;
			boolean continous = true;
			for(int i=z,j=0; i>=0&&j>=0&&i<SIZE&&j<SIZE; i--,j++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
			for(int i=SIZE-1,j=z; i>=0&&j>=0&&i<SIZE&&j<SIZE; i--,j++){
				if(board[i][j] == STONE && continous == true){
					counter++;
				}
				else if(board[i][j] == STONE && continous == false){
					counter++;
					continous = true;
				}
				else if(board[i][j] != STONE && continous == true){
					counter = 0;
					continous = false;
				}
				if(counter == 5)
					return true;
			}
		}
		return false;
	}

	// FIXME: 2017-04-17
	/** detect black win */
	public boolean detectBlackWin() {
		/* detect 5 in a row */
		int STONE = 2;
		for (int i = 0; i < SIZE; i++) {
			int counter = 0;
			boolean continous = true;
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
		}
		/* detect 5 in a column */
		for (int j = 0; j < SIZE; j++) {
			int counter = 0;
			boolean continous = true;
			for (int i = 0; i < SIZE; i++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
		}
		/* detect 5 in diagonals 
		 * */
		for (int z = 0; z < SIZE; z++) {
			int counter = 0;
			boolean continous = true;
			for (int i = z, j = 0; i < SIZE && j < SIZE; i++, j++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
			for (int i = 0, j = z; i < SIZE && j < SIZE; i++, j++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
		}
		for (int z = SIZE - 1; z >= 0; z--) {
			int counter = 0;
			boolean continous = true;
			for (int i = z, j = 0; i >= 0 && j >= 0 && i < SIZE && j < SIZE; i--, j++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
			for (int i = SIZE - 1, j = z; i >= 0 && j >= 0 && i < SIZE && j < SIZE; i--, j++) {
				if (board[i][j] == STONE && continous == true) {
					counter++;
				} else if (board[i][j] == STONE && continous == false) {
					counter++;
					continous = true;
				} else if (board[i][j] != STONE && continous == true) {
					counter = 0;
					continous = false;
				}
				if (counter == 5)
					return true;
			}
		}
		return false;
	}

}// end of class
