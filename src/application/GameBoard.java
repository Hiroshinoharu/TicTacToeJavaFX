package application;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane{
	
	// Create a 2D array to represent the game board
	private int [][] board;
	private final int BOARD_SIZE = 3;
	
	// Create a 2D array of buttons to represent the game board
	private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
	
	// Create two players: human and AI
	private Player humanPlayer;
	private AIPlayer aiPlayer;
	
	// Create a variable to keep track of the current player
	private int currentPlayer = 1;
	
	// Create a constructor to initialize the game board
	public GameBoard() {
		board = new int[BOARD_SIZE][BOARD_SIZE];
		setHumanPlayer(new Player(1) {
			@Override
			public Move findBestMove(GameBoard board) {
				// Human player does not need to find the best move
				return null;
			}
		});
		aiPlayer = new AIPlayer(-1); // AI player is player 2 as O
		intializeBoard();
	}
	
	private void intializeBoard() {
		// TODO Initialize the game board with buttons and add them to the GridPane
		for(int row = 0; row < BOARD_SIZE; row++) {
			for(int col = 0; col < BOARD_SIZE; col++) {
				Button btn = new Button();
				btn.setPrefSize(200, 200);
				int finalRow = row;
				int finalCol = col;
				btn.setOnAction(e -> handlePlayerMove(finalRow, finalCol));
				buttons[row][col] = btn;
				this.add(btn, col, row);
			}
		}
	}

	private void handlePlayerMove(int row, int col) {
		// TODO Auto-generated method stub
		if(currentPlayer == 1) {  // Ensure its the player's turn
			System.out.println("Player is making a move...");
			if(makeMove(row, col, currentPlayer)) {
				System.out.println("Move successful!");
				buttons[row][col].setText("X");  // Update the button text
				buttons[row][col].setDisable(true);  // Disable the button
				printBoardState(this);  // Print the board
				// Check the game status
				System.out.println("Checking game status...");
				if(checkWin() == 1) {
					System.out.println("Player wins!");
					endGame();
				} else if (checkWin() == 0) {
					System.out.println("It's a draw!");
					endGame();
				} else {
					System.out.println("Switching to AI player...");
					currentPlayer = -1; // Switch to AI player
					aiMove(); // AI makes a move
				}
			}
		}
	}

	
	private void aiMove() {
		if(currentPlayer == -1) {
			Move bestMove = aiPlayer.findBestMove(this);  // AI finds the best move
			System.out.println("AI is making a move...");
			if (makeMove(bestMove.getRow(), bestMove.getCol(), currentPlayer)) {
				System.out.println("Move successful!");
				buttons[bestMove.getRow()][bestMove.getCol()].setText("O"); // Update the button text
				buttons[bestMove.getRow()][bestMove.getCol()].setDisable(true); // Disable the button
				// Check the game status
				System.out.println("Checking game status...");
				if (checkWin() == -1) {
					System.out.println("AI wins!");
					endGame();
				} else if (checkWin() == 0) {
					System.out.println("It's a draw!");
					endGame();
				} else {
					System.out.println("Switching to player...");
					currentPlayer = 1; // Switch to player
				}
			}
		}
	}
	
	private void endGame() {
		// TODO Auto-generated method stub
		System.out.println("Game over!");
		resetBoard();  // Reset the game board
	}

	public boolean makeMove(int row, int col, int playerMark) {
	    System.out.println("Attempting to make move at: (" + row + ", " + col + ") for player: " + playerMark);
		if (row >= 0 && col >= 0 && row < 3 && col < 3) { // Check bounds and if the cell is empty
			if (playerMark == 0 || board[row][col] == 0) {
				board[row][col] = playerMark; // Make the move
				System.out.println("Move successful!");
				return true; // If the move is valid
			}
		}
		System.out.println("Move failed. Cell is already taken or out of bounds.");
		return false; // If the cell is not empty
	}
	
	public int checkWin() {
		// Implement logic to check rows, columns, and diagonals for a win
		// Return 1 if player 1 wins, -1 if player 2 wins, -2 if no one wins
		
		// Check rows
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
				return board[i][0];
			}
		}
		
		// Check columns
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
				return board[0][i];
			}
		}
		
		// Check diagonals
		if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
			return board[0][0];
		}
		
		if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
			return board[0][2];
		}
		
		// Check for a draw
		boolean boardFull = true;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == 0) {
					boardFull = false;
					break;
				}
			}
			if (!boardFull) {
				break;
			}
		}
		if (boardFull) {
			return 0;
		} 
		return -2;// Return -2 if the game is still in progress
	}
	
	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
				buttons[i][j].setText(""); // Clear the text on the buttons
				buttons[i][j].setDisable(false); // Re-enable the buttons
			}
		}
		System.out.println("Board reset.");
		currentPlayer = 1; // Reset the current player to player 1
	}
	
	private void printBoardState(GameBoard board) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board.getBoard()[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	

	public int[][] getBoard() {
		return board;
	}

	public Player getHumanPlayer() {
		return humanPlayer;
	}

	public void setHumanPlayer(Player humanPlayer) {
		this.humanPlayer = humanPlayer;
	}

}
