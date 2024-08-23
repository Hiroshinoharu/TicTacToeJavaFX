package application;

public class Minimax {
	
	// The minimax algorithm is a recursive algorithm used to choose the next move in a game.
	public int minimax(GameBoard board, int depth, boolean isMax) {
		int score = board.checkWin();
		if(score == 1) return score - depth; // If player 1 wins 
		if(score == -1) return score + depth; // If player 2 wins
		if(isBoardFull(board)) return 0; // If the board is full and no one wins
		
		if(isMax) {
			int bestScorer = Integer.MIN_VALUE;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board.getBoard()[i][j] == 0) { // Check if the cell is empty
						board.makeMove(i, j, 1); // Make the move
						score = minimax(board, depth + 1, false); // Recursively call minimax
						board.makeMove(i, j, 0); // Undo the move
						bestScorer = Math.max(score, bestScorer); // Update the best score
					}
				}
			}
			return bestScorer;
		} else {
			int bestScorer = Integer.MAX_VALUE;
			for(int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board.getBoard()[i][j] == 0) { // Check if the cell is empty
						board.makeMove(i, j, -1); // Make the move
						score = minimax(board, depth + 1, true); // Recursively call minimax
						board.makeMove(i, j, 0); // Undo the move
						bestScorer = Math.min(score, bestScorer); // Update the best score
					}
				}
			}
			return bestScorer;
		}
	}
	
	// Find the best move for the AI player
	public Move findBestMove(GameBoard board, int playerMark) {
		int bestVal = Integer.MIN_VALUE;
		Move bestMove = new Move(-1, -1);
		
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getBoard()[i][j] == 0) { // Check if the cell is empty
					board.makeMove(i, j, playerMark); // Make the move
					int moveVal = minimax(board, 0, playerMark == 1); // Call minimax
					board.makeMove(i, j, 0); // Undo the move

					if (moveVal > bestVal) { // Update the best move
						bestMove.row = i;
						bestMove.col = j;
						bestVal = moveVal;
					}
				}
			}
		}
		return bestMove;
	}
	
	public boolean isBoardFull(GameBoard board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getBoard()[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
