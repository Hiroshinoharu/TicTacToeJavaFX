package application;

// Minimax class for AI decision-making using the minimax algorithm with alpha-beta pruning
public class Minimax {

    // Minimax algorithm with alpha-beta pruning
    public int minimax(GameBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        int[][] winPositions = new int[3][2];  // Array to store potential winning positions
        int score = board.checkWin(winPositions);  // Check the current game status

        // Base case: return the score if the game is over
        if (score == 1) return 10 - depth; // AI win (higher score for faster wins)
        if (score == -1) return depth - 10; // Opponent win (lower score for faster losses)
        if (score == 0) return 0; // Draw condition (neutral score)

        if (isMaximizing) {
            int best = Integer.MIN_VALUE; // Initialize best score for maximizing player (AI)
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getBoard()[i][j] == 0) {  // Check if the cell is empty
                        board.makeMove(i, j, 1);  // AI makes a move (mark with 1)

                        // Recursive call to minimax for the minimizing opponent
                        int moveVal = minimax(board, depth + 1, false, alpha, beta);

                        board.makeMove(i, j, 0);  // Undo the move

                        best = Math.max(best, moveVal);  // Choose the best score for AI
                        alpha = Math.max(alpha, best);  // Update alpha for pruning

                        if (beta <= alpha) {
                            System.out.println("Alpha cut-off");
                            break; // Alpha cut-off
                        }
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE; // Initialize best score for minimizing player (opponent)
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getBoard()[i][j] == 0) {  // Check if the cell is empty
                        board.makeMove(i, j, -1);  // Opponent makes a move (mark with -1)

                        // Recursive call to minimax for the maximizing AI
                        int moveVal = minimax(board, depth + 1, true, alpha, beta);

                        board.makeMove(i, j, 0);  // Undo the move

                        best = Math.min(best, moveVal);  // Choose the best score for opponent
                        beta = Math.min(beta, best);  // Update beta for pruning

                        if (beta <= alpha) {
                            System.out.println("Beta cut-off");
                            break; // Beta cut-off
                        }
                    }
                }
            }
            return best;
        }
    }

    // Method to find the best move for the AI player
    public Move findBestMove(GameBoard board, int playerMark) {
        int bestVal = (playerMark == 1) ? Integer.MIN_VALUE : Integer.MAX_VALUE; // Initialize best value
        Move bestMove = new Move(-1, -1); // Default invalid move

        // Iterate through all cells to find the best move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == 0) { // Check if the cell is empty
                    board.makeMove(i, j, playerMark); // Make the move

                    // Evaluate move using minimax with alpha-beta pruning
                    int moveVal = minimax(board, 0, playerMark != 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

                    board.makeMove(i, j, 0); // Undo the move

                    // Update bestMove and bestVal for maximizing or minimizing player
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

        return bestMove; // Return the best move found
    }

    // Check if the game board is full (no more valid moves)
    public boolean isBoardFull(GameBoard board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == 0) {
                    return false; // If there is an empty cell, the board is not full
                }
            }
        }
        return true; // If all cells are filled, the board is full
    }
}
