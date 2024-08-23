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
	private int currentPlayer;
	
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
		currentPlayer = 1; // Human player as X
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
				btn.setOnAction(e -> handleMove(finalRow, finalCol));
				buttons[row][col] = btn;
				this.add(btn, col, row);
			}
		}
	}

	private void handleMove(int row, int col) {
		// TODO Auto-generated method stub
		if(makeMove(row, col, currentPlayer)) {
			buttons[row][col].setText(currentPlayer == 1 ? "X" : "O");
			int result = checkWin();
			if(result != -2) {
				// Handle win or draw
				System.out.println(result == 1 ? "Player 1 wins!" : result == -1 ? "Player 2 wins!" : "It's a draw!");
				resetBoard();
			} else {
                currentPlayer = currentPlayer == 1 ? -1 : 1; // Switch player
                if (currentPlayer == -1) {
                	aiMove();
                }
            }
		}
	}

	private void aiMove() {
		// TODO Auto-generated method stub
		Move bestMove = aiPlayer.findBestMove(this);
		makeMove(bestMove.row, bestMove.col, aiPlayer.getPlayerMark());
		buttons[bestMove.row][bestMove.col].setText("O");
		int result = checkWin();
		if (result != -2) {
			// Handle win or draw
			System.out.println(result == 1 ? "Player 1 wins!" : result == -1 ? "Player 2 wins!" : "It's a draw!");
			resetBoard();
		} else {
			currentPlayer = 1; // Switch player
		}
	}

	public boolean makeMove(int row, int col, int playerMark) {
		if (board[row][col] == 0) { // If the cell is empty
			board[row][col] = playerMark;
			return true;
		}
		return false;
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
			for(int i = 0; i < BOARD_SIZE; i++) {
				if (checkWin() != -2) {
					return -2;
				}
			}
		} 
		return 0;// Return 0 if it's a draw
	}
	
	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
			}
		}
		resetButtons();
		currentPlayer = 1; // Reset the current player to player 1
	}
	
	private void resetButtons() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				buttons[i][j].setText("");
			}
		}
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
