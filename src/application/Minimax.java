package application;

public class Minimax {
	
	// The minimax algorithm is a recursive algorithm used to choose the next move in a game.
	public int minimax(GameBoard board, int depth, boolean isMaximizing) {
	    int score = board.checkWin();

	    // Base case: return the score if the game is over
	    if (score == 1 || score == -1 || score == 0) {
	        return score;
	    }

	    if (isMaximizing) {
	        int best = Integer.MIN_VALUE;
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board.getBoard()[i][j] == 0) {  // Ensure the cell is empty before making a move
	                    board.makeMove(i, j, 1);  // AI makes a move
	                    printBoardState(board);  // Print the board state

	                    int moveVal = minimax(board, depth + 1, false); // Recursive call
	                    System.out.println("Move value: " + moveVal);

	                    board.makeMove(i, j, 0);  // Undo the move
	                    printBoardState(board);  // Print the board state after undoing the move

	                    best = Math.max(best, moveVal);  // Keep track of the best value
	                }
	            }
	        }
	        return best;
	    } else {
	        int best = Integer.MAX_VALUE;
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board.getBoard()[i][j] == 0) {  // Ensure the cell is empty before making a move
	                    board.makeMove(i, j, -1);  // Opponent makes a move
	                    printBoardState(board);  // Print the board state

	                    int moveVal = minimax(board, depth + 1, true);
	                    System.out.println("Move value: " + moveVal);

	                    board.makeMove(i, j, 0);  // Undo the move
	                    printBoardState(board);  // Print the board state after undoing the move

	                    best = Math.min(best, moveVal);  // Keep track of the best value
	                }
	            }
	        }
	        return best;
	    }
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


	public Move findBestMove(GameBoard board, int playerMark) {
	    int bestVal = (playerMark == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
	    Move bestMove = new Move(-1, -1); // Default invalid move
	    
	    System.out.println("Player mark: " + playerMark);

	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            if (board.getBoard()[i][j] == 0) { // Check if the cell is empty
	            	System.out.println("Checking cell: " + i + " " + j);
	                board.makeMove(i, j, playerMark); // Make the move

	                // Determine whether to maximize or minimize
	                int moveVal = minimax(board, 0, playerMark != 1); // Call minimax
	                System.out.println("Move value: " + moveVal);
	                
	                // Undo the move
	                board.makeMove(i, j, 0);
	                System.out.println("Undoing move...");

	                // Update bestMove and bestVal based on whether AI is maximizing or minimizing
	                if ((playerMark == 1 && moveVal > bestVal) || (playerMark == -1 && moveVal < bestVal)) {
	                    bestMove.setRow(i);
	                    bestMove.setCol(j);
	                    bestVal = moveVal;
	                    System.out.println("Best move: " + bestMove.getRow() + " " + bestMove.getCol());
	                }
	            }
	        }
	    }

	    // Safety check: Ensure bestMove is valid
	    if (bestMove.getRow() == -1 || bestMove.getCol() == -1) {
	    	System.out.println("AI could not find a valid move., something went wrong.");
	        throw new IllegalStateException("AI could not find a valid move.");
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
