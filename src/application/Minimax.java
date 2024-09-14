package application;

public class Minimax {

    // The minimax algorithm with alpha-beta pruning
    public int minimax(GameBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        int[][] winPositions = new int[3][2];  // Array to store winning positions
        int score = board.checkWin(winPositions);  // Check if the game is over

        // Base case: return the score if the game is over
        if (score == 1) return 10 - depth; // Favor faster wins for AI
        if (score == -1) return depth - 10; // Favor slower losses for AI
        if (score == 0) return 0; // Draw condition

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getBoard()[i][j] == 0) {  // Ensure the cell is empty before making a move
                        board.makeMove(i, j, 1);  // AI makes a move

                        int moveVal = minimax(board, depth + 1, false, alpha, beta); // Recursive call

                        board.makeMove(i, j, 0);  // Undo the move

                        best = Math.max(best, moveVal);  // Keep track of the best value
                        alpha = Math.max(alpha, best);  // Update alpha value for pruning purposes

                        if (beta <= alpha) {
                        	System.out.println("Alpha cut-off");
                            break; // Beta cut-off
                        }
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

                        int moveVal = minimax(board, depth + 1, true, alpha, beta);

                        board.makeMove(i, j, 0);  // Undo the move

                        best = Math.min(best, moveVal);  // Keep track of the best value
                        beta = Math.min(beta, best);  // Update beta

                        if (beta <= alpha) {
                        	System.out.println("Beta cut-off");
                            break; // Alpha cut-off
                        }
                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove(GameBoard board, int playerMark) {
        int bestVal = (playerMark == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = new Move(-1, -1); // Default invalid move
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == 0) { // Check if the cell is empty
                    board.makeMove(i, j, playerMark); // Make the move

                    // Call minimax with alpha-beta pruning
                    int moveVal = minimax(board, 0, playerMark != 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    
                    // Undo the move
                    board.makeMove(i, j, 0);

                    // Update bestMove and bestVal based on whether AI is maximizing or minimizing
                    if ((playerMark == 1 && moveVal > bestVal) || (playerMark == -1 && moveVal < bestVal)) {
                        bestMove.setRow(i);
                        bestMove.setCol(j);
                        bestVal = moveVal;
                    }
                }
            }
        }

        // Safety check: Ensure bestMove is valid
        if (bestMove.getRow() == -1 || bestMove.getCol() == -1) {
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
