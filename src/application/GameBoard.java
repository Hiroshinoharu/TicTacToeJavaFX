package application;

public class GameBoard {
	private int [][] board;
	private final int BOARD_SIZE = 3;
	
	public GameBoard() {
		board = new int[BOARD_SIZE][BOARD_SIZE];
	}
	
	public boolean makeMove(int row, int col, int player) {
		if (board[row][col] == 0) { // If the cell is empty
			board[row][col] = player;
			return true;
		}
		return false;
	}
	
	public int checkWin() {
		// Implement logic to check rows, columns, and diagonals for a win
		// Return 1 if player 1 wins, -1 if player 2 wins, -2 if no one wins
		return -2;
	}
	
	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
			}
		}
	}

	public int[][] getBoard() {
		return board;
	}
	
}
